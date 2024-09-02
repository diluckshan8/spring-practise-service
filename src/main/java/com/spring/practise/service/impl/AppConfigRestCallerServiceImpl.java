package com.spring.practise.service.impl;

import static com.spring.practise.common.CommonConstants.APP_CONFIG_URL_BASE;
import static com.spring.practise.common.CommonConstants.FEATURE_FLAG_URL_BASE;

import com.spring.practise.exception.CommonsException;
import com.spring.practise.service.AppConfigRestCallerService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AppConfigRestCallerServiceImpl implements AppConfigRestCallerService {

    @Value("${app.config.serviceName}")
    private String serviceName;
    @Value("${app.config.environmentName}")
    private String environmentName;
    @Value("${app.config.url}")
    private String url;

    public String getUriBase(String key, String app) {
            return String.format(url + FEATURE_FLAG_URL_BASE , serviceName, environmentName, app, key);
    }

    public String getUriBase(String app) {
        return String.format(url + APP_CONFIG_URL_BASE , serviceName, environmentName, app);
    }

    public String getCommonsUriBase(String key) { return getUriBase(key,"COMMON"); }

    public String getCommonsUriBase() {
        return getUriBase("COMMON");
    }

    public ResponseEntity<String> getAppConfigResponse(String key, String app) throws CommonsException {
        AtomicBoolean isDataAvailable= new AtomicBoolean(true);
        ResponseEntity<String> result;
        RestClient restClient = RestClient.create();
        result= restClient.get()
                .uri(buildUrl(key, app, false))
                .retrieve()
                .onStatus(status -> status.value() == 400, (request, response) -> isDataAvailable.set(false))
                .toEntity(String.class);

        if(!isDataAvailable.get()){
            result= getAppConfigResponseFromCommon(key, app);
        }
        return result;
    }

    public ResponseEntity<String> getAppConfigResponseFromCommon(String key, String app) throws CommonsException {
        RestClient restClient = RestClient.create();
        return restClient.get()
                    .uri(buildUrl(key, app, true))
                    .retrieve()
                    .onStatus(status -> status.value() == 400, (request, response) -> {
                        throw new CommonsException("Configuration data missing one or more config values");
                    })
                    .toEntity(String.class);
    }

    public String buildUrl(String key, String app, boolean common) {
        String url;
        if(key == null){
            if(common){
                url = getCommonsUriBase();
            } else {
                url = getUriBase(app);
            }
        } else {
            if(common){
                url = getCommonsUriBase(key);
            } else {
                url = getUriBase(key, app);
            }
        }
        return url;
    }
}

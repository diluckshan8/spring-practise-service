package com.spring.practise.service.impl;

import static com.spring.practise.common.CommonConstants.APP_COMMON_CONFIG;
import static com.spring.practise.mapper.CommonObjectMapper.mapStringToClass;

import com.spring.practise.exception.ConfigParseException;
import com.spring.practise.service.AppConfigRestCallerService;
import com.spring.practise.service.AppConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppConfigServiceImpl implements AppConfigService {

  private final AppConfigRestCallerService appConfigRestCallerService;

  public <T> T getConfig(String appName, Class<T> clazz) {
    try {
      ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(null, appName);
      return mapStringToClass(clazz,result.getBody());
    } catch (JsonProcessingException e) {
      throw new ConfigParseException(e);
    }
  }

  public <T> T getConfig(Class<T> clazz) {
    try {
      ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(null, APP_COMMON_CONFIG);
      return mapStringToClass(clazz,result.getBody());
    } catch (JsonProcessingException e) {
      throw new ConfigParseException(e);
    }
  }
  public String getConfigText(String appName) {
    ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(null, appName);
    return result.getBody();
  }

  public String getConfigText(){
    ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(null, APP_COMMON_CONFIG);
    return result.getBody();
  }


}

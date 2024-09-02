package com.spring.practise.service.impl;

import static com.spring.practise.mapper.CommonObjectMapper.mapToJSONNode;

import com.spring.practise.exception.ConfigParseException;
import com.spring.practise.mapper.CommonObjectMapper;
import com.spring.practise.model.FeatureFlag;
import com.spring.practise.service.AppConfigRestCallerService;
import com.spring.practise.service.FeatureFlagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FeatureFlagServiceImpl implements FeatureFlagService {

    @Value("${app.config.applicationName}")
    private String applicationName;

    private final AppConfigRestCallerService appConfigRestCallerService;
    @Override
    public boolean isFeatureFlagEnabled(String key) {
        return isFeatureFlagEnabled(this.applicationName,key);

    }

    @Override
    public Optional<FeatureFlag> getFeatureFlag(String key) {
        return getFeatureFlag(this.applicationName,key);
    }

    @Override
    public boolean isFeatureFlagEnabled(String app, String key) {
        ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(key,app);

        try {
            JsonNode rootNode = mapToJSONNode(result.getBody());
            return rootNode.path("enabled").asBoolean();
        } catch (JsonProcessingException e) {
            throw new ConfigParseException(e);
        }
    }

    @Override
    public Optional<FeatureFlag> getFeatureFlag(String app, String key)  {
        FeatureFlag featureFlag = new FeatureFlag();

        ResponseEntity<String> result = appConfigRestCallerService.getAppConfigResponse(key,app);

        JsonNode rootNode;
        try {
            rootNode = mapToJSONNode(result.getBody());
        } catch (JsonProcessingException e) {
            throw new ConfigParseException(e);
        }
        JsonNode enableNode = rootNode.path("enabled");

        if(enableNode.asBoolean()){

            JsonNode sitesNode = rootNode.path("sites");
            JsonNode usersNode = rootNode.path("users");
            List<String> sites;
            try {
                sites = CommonObjectMapper.mapStringToTypeReference(new TypeReference<List<String>>(){},sitesNode.toString());
            } catch (JsonProcessingException e) {
                throw new ConfigParseException(e);
            }
            List<String> users;
            try {
                users = CommonObjectMapper.mapStringToTypeReference(new TypeReference<List<String>>(){},usersNode.toString());
            } catch (JsonProcessingException e) {
                throw new ConfigParseException(e);
            }
            featureFlag.setSites(sites);
            featureFlag.setUsers(users);
        }
        return Optional.of(featureFlag);
    }

}

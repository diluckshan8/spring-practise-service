package com.spring.practise.service;

import com.spring.practise.exception.CommonsException;
import org.springframework.http.ResponseEntity;

public interface AppConfigRestCallerService {

  /**
   * Calls AWS AppConfig REST APIs
   * @param app The application context of the appConfig to check.
   * @param key The key of the appConfig to check.
   * @return Response from the AWS AppConfig REST API
   * @throws CommonsException Invalid key or app parameters
   */
  ResponseEntity<String> getAppConfigResponse(String key, String app) throws CommonsException;

}

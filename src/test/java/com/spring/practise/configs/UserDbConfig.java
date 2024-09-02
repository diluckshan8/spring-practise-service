package com.spring.practise.configs;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

import com.spring.practise.model.UserDocument;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.testcontainers.containers.localstack.LocalStackContainer;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@TestConfiguration(proxyBeanMethods = false)
public class UserDbConfig {

  @Bean
  public DynamoDbClient dynamoDbClient(AwsCredentialsProvider credentialsProvider, LocalStackContainer localstack) {
    return DynamoDbClient.builder()
        .credentialsProvider(credentialsProvider)
        .endpointOverride(localstack.getEndpointOverride(DYNAMODB))
        .build();
  }

  @EventListener
  @SuppressWarnings("unchecked")
  public void onApplicationReady(ApplicationReadyEvent applicationReadyEvent) {
    ApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
    DynamoDbTable<UserDocument> userTable = applicationContext.getBean("userTable", DynamoDbTable.class);
    userTable.createTable();
  }
}
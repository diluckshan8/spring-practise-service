package com.spring.practise.configs;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@TestConfiguration(proxyBeanMethods = false)
public class AwsConfig {

  private static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:latest");

  @Bean
  public LocalStackContainer localStackContainer() {
    return new LocalStackContainer(LOCALSTACK_IMAGE_NAME).withServices(DYNAMODB);
  }

  @Bean
  public AwsCredentialsProvider awsCredentialsProvider(LocalStackContainer localStack) {
    AwsBasicCredentials creds = AwsBasicCredentials.create(localStack.getAccessKey(), localStack.getSecretKey());
    return StaticCredentialsProvider.create(creds);
  }
}

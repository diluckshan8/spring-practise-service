package com.spring.practise.configuration;

import com.spring.practise.model.UserDocument;
import com.spring.practise.repo.UserRepository;
import com.spring.practise.repo.UserRepositoryImpl;
import com.spring.practise.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {

  @Bean
  @Profile("!test")
  public AwsCredentialsProvider awsCredentialsProvider() {
    return ContainerCredentialsProvider.builder().build();
  }

  @Bean
  @Profile("!test")
  public DynamoDbClient dynamoDbClient(AwsCredentialsProvider credentialsProvider) {
    return DynamoDbClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.EU_WEST_1)
        .build();
  }

  @Bean
  public DynamoDbTable<UserDocument> userTable(DynamoDbClient dynamoDbClient, @Value("${user.table}") String userTableName) {
    DynamoDbEnhancedClient dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build();
    TableSchema<UserDocument> userDocumentSchema = TableSchema.fromBean(UserDocument.class);
    return dynamoDbEnhancedClient.table(userTableName, userDocumentSchema);
  }

  @Bean
  public UserRepository userRepository(DynamoDbTable<UserDocument> userTable) {
    return new UserRepositoryImpl(userTable);
  }

  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserService(userRepository);
  }

}

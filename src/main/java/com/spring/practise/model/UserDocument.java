package com.spring.practise.model;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {

  private UUID userId;
  private String userName;

  @DynamoDbPartitionKey
  public UUID getuserId() {
    return userId;
  }

  public static UserDocument fromUser(User user) {
    return new UserDocument(user.userId().value(), user.userName());
  }

  public User toUser() {
    return new User(UserId.from(userId), userName);
  }
}
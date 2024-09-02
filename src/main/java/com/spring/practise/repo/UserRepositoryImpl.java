package com.spring.practise.repo;

import com.spring.practise.model.User;
import com.spring.practise.model.UserDocument;
import com.spring.practise.model.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final DynamoDbTable<UserDocument> userTable;

  @Override
  public UserId save(User user) {
    userTable.putItem(UserDocument.fromUser(user));
    return user.userId();
  }

  @Override
  public Optional<User> findById(UserId userId) {
    Key userIdKey = Key.builder()
        .partitionValue(userId.value().toString())
        .build();
    return Optional.ofNullable(userTable.getItem(userIdKey))
        .map(UserDocument::toUser);
  }
}

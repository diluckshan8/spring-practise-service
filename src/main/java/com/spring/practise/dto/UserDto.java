package com.spring.practise.dto;

import com.spring.practise.model.User;
import java.util.UUID;

public record UserDto(UUID userId, String eventName) {

  public static UserDto fromUser(User user) {
    return new UserDto(user.userId().value(), user.userName());
  }
}

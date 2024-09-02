package com.spring.practise.model;

import java.util.UUID;

public record UserId(UUID value) {

  public static UserId create() {
    return new UserId(UUID.randomUUID());
  }

  public static UserId from(UUID value) {
    return new UserId(value);
  }
}

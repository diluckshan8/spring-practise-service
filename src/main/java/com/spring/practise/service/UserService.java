package com.spring.practise.service;

import com.spring.practise.model.CreateUserCommand;
import com.spring.practise.model.User;
import com.spring.practise.model.UserId;
import com.spring.practise.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserId handle(CreateUserCommand createUserCommand) {
    User user = new User(UserId.create(), createUserCommand.userName());
    return userRepository.save(user);
  }
}

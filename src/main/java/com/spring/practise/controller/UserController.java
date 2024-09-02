package com.spring.practise.controller;

import com.spring.practise.dto.UserDto;
import com.spring.practise.model.CreateUserCommand;
import com.spring.practise.model.UserId;
import com.spring.practise.repo.UserRepository;
import com.spring.practise.service.UserService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.USERS_PATH)
@RequiredArgsConstructor
public class UserController {

  static final String USERS_PATH = "users";

  private final UserService userService;
  private final UserRepository userRepository;

  @GetMapping("{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
    return userRepository.findById(UserId.from(id))
        .map(UserDto::fromUser)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody CreateUserCommand createUserCommand) {
    UserId newUserId = userService.handle(createUserCommand);
    URI newUserUri = URI.create("/%s/%s".formatted(USERS_PATH, newUserId.value()));
    return ResponseEntity.created(newUserUri).build();
  }
}

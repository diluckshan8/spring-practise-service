package com.spring.practise.repo;

import com.spring.practise.model.User;
import com.spring.practise.model.UserId;
import java.util.Optional;

public interface UserRepository {

  UserId save(User user);

  Optional<User> findById(UserId userId);

}

package com.User.service;

import com.User.Exceptions.UserNotFoundException;
import com.User.entity.User;
import com.User.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User createUser(User user) {
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
    userOptional.orElseThrow(() -> new UserNotFoundException("User with email: " + user.getEmail() + " was not found"));

  }
  public User getUserById(Long id) {
    Optional<User> userOptional = userRepository.findBy(id);
  }
}

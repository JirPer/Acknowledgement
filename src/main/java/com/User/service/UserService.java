package com.User.service;

import com.User.Exceptions.UserExistsException;
import com.User.Exceptions.UserNotFoundException;
import com.User.entity.User;
import com.User.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User createUser(User user) {
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
    if(userOptional.isPresent()) {
      throw new UserExistsException("User with email:" + userOptional.get().getEmail() + " is already present");
    }
    return userRepository.save(user);
  }
  public User getUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " + id + " was not found"));
    return userOptional.get();
  }
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}

package com.User.Service;

import com.User.Entity.User;
import com.User.Exceptions.UserExistsException;
import com.User.Exceptions.UserNotFoundException;
import com.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public User createUser(User user) {
    Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
    if (userOptional.isPresent()) {
      throw new UserExistsException(
          "User with email:" + userOptional.get().getEmail() + " is already present");
    }
    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new UserNotFoundException("User with id: " + id + " was not found"));
    return userOptional.get();
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public void deleteUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new UserNotFoundException("User with id: " + id + " was not found"));
  }

  @Transactional
  public User editUserById(Long id, String email, String name) {
    Optional<User> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " + id + " was not found"));
    if(email != null && !Objects.equals(userOptional.get().getEmail(), email)) {
      userOptional.get().setEmail(email);
    }
    if(name != null && !Objects.equals(userOptional.get().getName(), name)) {
      userOptional.get().setName(name);
    }
    return userOptional.get();
  }

}
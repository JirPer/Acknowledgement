package com.aoa.user.service;

import com.aoa.exception.ApiException400;
import com.aoa.exception.ApiException404;
import com.aoa.exception.ErrorCause;
import com.aoa.user.entity.UserDetail;
import com.aoa.user.repository.UserRepository;
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
  public UserDetail createUser(UserDetail userDetail) {
    Optional<UserDetail> userOptional = userRepository.findByEmail(userDetail.getEmail());
    if (userOptional.isPresent()) {
      throw new ApiException400(String.format("User with email %s already exists", userDetail.getEmail()), ErrorCause.ENTITY_ALREADY_EXISTS);
    }
    return userRepository.save(userDetail);
  }

  public UserDetail getUserById(Long id) {
    Optional<UserDetail> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new ApiException404(String.format("User with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));
    return userOptional.get();
  }

  public List<UserDetail> getAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public void deleteUserById(Long id) {
    Optional<UserDetail> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new ApiException404(String.format("User with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));
  }

  @Transactional
  public UserDetail editUserById(Long id, String email, String name) {
    Optional<UserDetail> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(() -> new ApiException404(String.format("User with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));
    if(email != null && !Objects.equals(userOptional.get().getEmail(), email)) {
      userOptional.get().setEmail(email);
    }
    if(name != null && !Objects.equals(userOptional.get().getName(), name)) {
      userOptional.get().setName(name);
    }
    return userOptional.get();
  }

}
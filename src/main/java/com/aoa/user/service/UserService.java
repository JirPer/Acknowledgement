package com.aoa.user.service;

import com.aoa.exception.ApiException400;
import com.aoa.exception.ApiException404;
import com.aoa.exception.ErrorCause;
import com.aoa.user.dto.UserDetailDTO;
import com.aoa.user.entity.UserDetail;
import com.aoa.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Transactional
  public UserDetailDTO createUser(UserDetail userDetail) {
    Optional<UserDetail> userOptional = userRepository.findByEmail(userDetail.getEmail());
    if (userOptional.isPresent()) {
      throw new ApiException400(String.format("User with email %s already exists", userDetail.getEmail()), ErrorCause.ENTITY_ALREADY_EXISTS);
    }
    userRepository.save(userDetail);
    return modelMapper.map(userDetail, UserDetailDTO.class);
  }

  public UserDetailDTO getUserById(Long id) {
    Optional<UserDetail> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new ApiException404(String.format("User with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));
    return modelMapper.map(userOptional.get(), UserDetailDTO.class);
  }

  public List<UserDetailDTO> getAllUsers() {
    return userRepository.findAll().stream()
        .map(users -> modelMapper.map(users, UserDetailDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional
  public UserDetail deleteUserById(Long id) {
    Optional<UserDetail> userOptional = userRepository.findById(id);
    userOptional.orElseThrow(
        () -> new ApiException404(String.format("User with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));

    return userOptional.get();
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
    return userRepository.save(userOptional.get());
  }

}
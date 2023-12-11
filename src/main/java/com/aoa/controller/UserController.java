package com.aoa.controller;

import com.aoa.dto.UserDetailDTO;
import com.aoa.entity.UserDetail;
import com.aoa.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;
  @GetMapping("/{id}")
  public ResponseEntity<UserDetailDTO> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<UserDetailDTO> createUser(@RequestBody @Valid UserDetail userDetail) {
    return new ResponseEntity<>(userService.createUser(userDetail), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserDetailDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<UserDetail> deleteUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.deleteUserById(id));
  }
  @PostMapping("/{id}")
    public ResponseEntity<UserDetail> editUserById(@PathVariable Long id, @RequestParam(required = false) String name,
      @RequestParam (required = false) String email) {
    return ResponseEntity.ok(userService.editUserById(id, name, email));
  }

}

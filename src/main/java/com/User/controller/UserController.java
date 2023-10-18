package com.User.controller;

import com.User.entity.UserDetail;
import com.User.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<UserDetail> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<UserDetail> createUser(@RequestBody UserDetail userDetail) {
    return ResponseEntity.ok(userService.createUser(userDetail));
  }

  @GetMapping
  public ResponseEntity<List<UserDetail>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  @DeleteMapping("/{id}")
  public void deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
  }
  @PostMapping("/{id}")
    public ResponseEntity<UserDetail> editUserById(@PathVariable Long id, @RequestParam(required = false) String name,
      @RequestParam (required = false) String email) {
    return ResponseEntity.ok(userService.editUserById(id, name, email));
  }

}

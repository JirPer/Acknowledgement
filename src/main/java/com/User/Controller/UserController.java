package com.User.Controller;

import com.User.Entity.User;
import com.User.Service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  @DeleteMapping("/{id}")
  public void deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
  }
  @PostMapping("/{id}")
    public ResponseEntity<User> editUserById(@PathVariable Long id, @RequestParam(required = false) String name,
      @RequestParam (required = false) String email) {
    return ResponseEntity.ok(userService.editUserById(id, name, email));
  }

}

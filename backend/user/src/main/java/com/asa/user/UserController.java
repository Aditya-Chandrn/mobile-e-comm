package com.asa.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asa.user.models.Product;
import com.asa.user.models.User;
import com.asa.user.response.UserCreationResponse;
import com.asa.user.response.UserEditResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("")
  public String getMethodName() {
    return new String("hello guys");
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody User user) {
    Object response = userService.login(user);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public ResponseEntity<UserCreationResponse> createUser(@RequestBody User user) {
    String userId = userService.createUser(user);
    UserCreationResponse response = new UserCreationResponse("New user created successfully", userId);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/edit")
  public ResponseEntity<UserEditResponse> updateUser(@RequestBody User user) {
    String userId = user.getUserId();
    User editedUser = userService.editUser(userId, user);
    UserEditResponse response = new UserEditResponse("User updated successfully", editedUser);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteUser(@RequestParam String userId) {
    boolean result = userService.deleteUser(userId);
    if (result == true)
      return ResponseEntity.ok(String.format("User %s deleted successfully", userId));
    else
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user " + userId);
  }

  @PostMapping("/add-to-cart")
  public ResponseEntity<Object> addToCart(@RequestBody Map<String, Object> req)
      throws NoSuchFieldException, IllegalAccessException {
    String userId = (String) req.get("userId");
    ObjectMapper mapper = new ObjectMapper();
    Product product = mapper.convertValue(req.get("product"), Product.class);
    Object response = userService.addToCart(userId, product);
    return ResponseEntity.ok(response);
  }
}

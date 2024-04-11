package com.asa.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asa.user.models.Product;
import com.asa.user.models.User;

import jakarta.ws.rs.NotFoundException;

@Service
public class UserService {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private UserRepository repository;

  /**************************************************
   * Login
   ***************************************************/
  public Object login(User user) {
    User existingUser = repository.findByEmail(user.getEmail());
    boolean passwordMatches = encoder.matches(user.getPassword(), existingUser.getPassword());
    if (!passwordMatches)
      return "Invalid Credentials";
    existingUser.setPassword(null);
    return existingUser;
  }

  /**************************************************
   * Create Users
   ***************************************************/
  public String createUser(User user) {
    String encodedPassword = encoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    User savedUser = repository.save(user);

    String userId = savedUser.getUserId();
    return userId;
  }

  /**************************************************
   * Edit User
   ***************************************************/
  public User editUser(String userId, User updatedUserData) {
    // Retrieve the existing user from the database
    User existingUser = repository.findById(userId).orElse(null);

    if (existingUser != null) {
      // Update the fields of the existing user with the new data
      existingUser.setName(updatedUserData.getName());
      existingUser.setEmail(updatedUserData.getEmail());
      existingUser.setImage(updatedUserData.getImage());

      // Save the modified user back to the database
      return repository.save(existingUser);
    } else {
      // Handle the case where the user with the given ID is not found
      throw new NotFoundException("User not found with ID: " + userId);
    }
  }

  /**************************************************
   * Delete User
   ***************************************************/
  public boolean deleteUser(String userId) {
    // Retrieve the existing user from the database
    User existingUser = repository.findById(userId).orElse(null);
    if (existingUser != null) {
      repository.delete(existingUser);
      return true;
    } else {
      throw new NotFoundException("User not found with ID: " + userId);
    }
  }

  /**************************************************
   * Add To Cart
   ***************************************************/
  public Object addToCart(String userId, Product product) throws NoSuchFieldException, IllegalAccessException {
    Optional<User> optionalUser = repository.findById(userId);
    if (!optionalUser.isPresent())
      return "User doesn't exist";
    User user = optionalUser.get();

    String productId = product.getProductId();
    String fetchFieldValueUrl = String.format("http://localhost:5000/product/get-field?productId=%s&fieldName=quantity",
        productId);
    ResponseEntity<String> response = restTemplate.getForEntity(fetchFieldValueUrl, String.class);
    int quantityLeft = Integer.parseInt(response.getBody());

    if (product.getQuantity() > quantityLeft)
      return "Quantity limit exceeded";
    List<Product> cart = user.getCart();

    int index = -1;
    for (int i=0; i< cart.size(); i++) {
      if (cart.get(i).getProductId().equals(productId)) {
        index=i;
        break;
      }
    }

    if(index == -1) cart.add(product);
    else cart.get(index).setQuantity(product.getQuantity());
    user.setCart(cart);
    repository.save(user);
    return cart;
  }
}

package com.asa.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="mobiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  private String productId;
  private String productName;
  private float price;
  private String image;
  private String description;
  private String[] features;
}

package com.asa.product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

// spring boot imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// mongo imports
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@RestController
@RequestMapping("/product")
public class ProductController {
  @Autowired
  private MongoClient client;

  @Autowired
  private MongoConverter convertor;

  @Autowired
  private Environment environment;

  /**************************************************
   * Fetch products by applying filter
   ***************************************************/
  @GetMapping("")
  public ResponseEntity<List<Product>> filterProducts(
      @RequestParam(required = false, defaultValue = "") String name,
      @RequestParam(required = false) String[] companies,
      @RequestParam(required = false) Float lowerPrice,
      @RequestParam(required = false) Float upperPrice) {

    // get collection object
    String databaseName = environment.getProperty("spring.data.mongodb.database");
    MongoDatabase database = client.getDatabase(databaseName);
    MongoCollection<Document> collection = database.getCollection("mobiles");

    System.out.println("---------<><><><><><><><>--------");
    if (lowerPrice == null)
      lowerPrice = 0.0f;
    if (upperPrice == null)
      upperPrice = 99999999.9f;

    // configure filters
    String nameRegex = String.format("(?i).*%s.*", name);
    Bson nameFilter = Filters.regex("name", nameRegex);
    Bson priceFilter = Filters.and(Filters.gt("price", lowerPrice), Filters.lt("price", upperPrice));

    Bson filter = Filters.and(nameFilter, priceFilter);

    if (companies != null && companies.length > 0) {
      // make case insensitive check for companies
      List<Bson> companyFilters = new ArrayList<>();
      for (String company : companies) {
        companyFilters.add(Filters.regex("company", Pattern.compile(company, Pattern.CASE_INSENSITIVE)));
      }
      Bson finalCompanyFilter = Filters.or(companyFilters);
      filter = Filters.and(filter, finalCompanyFilter);
    }

    // search based on filter and store in a list
    List<Product> productList = new ArrayList<Product>();
    FindIterable<Document> productDocs = collection.find(filter);

    productDocs.forEach(product -> productList.add(convertor.read(Product.class, product)));

    return ResponseEntity.ok(productList);
  }

}

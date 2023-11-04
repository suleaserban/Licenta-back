package com.example.nutriCare.Controllers;

import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import com.example.nutriCare.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createOrUpdateProduct(product);
    }

    @PostMapping("/{productId}/factors")
    public ResponseEntity<?> addFactorsToProduct(@PathVariable Long productId, @RequestBody List<ProductFactor> factors) {
        productService.addFactorsToProduct(productId, factors);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productService.getProductById(id);
        // Actualizează proprietățile produsului cu cele din productDetails
        // De exemplu: product.setName(productDetails.getName());
        // ...
        return productService.createOrUpdateProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
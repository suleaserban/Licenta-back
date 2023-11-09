package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.ProductFactorDTO;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import com.example.nutriCare.Exceptions.ResourceNotFoundException;
import com.example.nutriCare.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/{numeProdus}/factors")
    public ResponseEntity<?> addFactorsToProduct(@PathVariable String numeProdus, @RequestBody List<ProductFactorDTO> factorDtos) {
        try {
            List<ProductFactor> factors = factorDtos.stream()
                    .map(dto -> new ProductFactor(dto.getNumeFactor(), dto.getValoare()))
                    .collect(Collectors.toList());
            productService.addFactorsToProduct(numeProdus, factors);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
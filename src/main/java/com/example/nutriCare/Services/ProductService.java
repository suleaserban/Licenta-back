package com.example.nutriCare.Services;

import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import com.example.nutriCare.Exceptions.ResourceNotFoundException;
import com.example.nutriCare.Repositories.ProductFactorRepository;
import com.example.nutriCare.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductFactorRepository productFactorRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductFactorRepository productFactorRepository) {
        this.productRepository = productRepository;
        this.productFactorRepository = productFactorRepository;
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    public void addFactorsToProduct(String numeProdus, List<ProductFactor> factors) {
        Product product = productRepository.findByNume(numeProdus)
                .orElseThrow(() -> new ResourceNotFoundException("Nu am gasit produs cu numele" + numeProdus));

        factors.forEach(factor -> {
            factor.setProduct(product);
            productFactorRepository.save(factor);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
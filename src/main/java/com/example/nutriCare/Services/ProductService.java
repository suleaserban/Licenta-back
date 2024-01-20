package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.ProductDTO;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import com.example.nutriCare.Exceptions.ResourceNotFoundException;
import com.example.nutriCare.Repositories.ProductFactorRepository;
import com.example.nutriCare.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductFactorRepository productFactorRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductFactorRepository productFactorRepository) {
        this.productRepository = productRepository;
        this.productFactorRepository = productFactorRepository;
    }


    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
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

    private ProductDTO convertToDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNume(product.getNume());
        dto.setDescriere(product.getDescriere());
        dto.setBeneficii(product.getBeneficii());
        dto.setIngrediente(product.getIngrediente());
        dto.setMod_administrare(product.getMod_administrare());
        dto.setContra_indicatii(product.getContra_indicatii());
        dto.setProducator(product.getProducator());


        return dto;
    }

}
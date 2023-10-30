package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.Product;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@EntityScan
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

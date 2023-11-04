package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.ProductFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFactorRepository extends JpaRepository<ProductFactor, Long> {

}
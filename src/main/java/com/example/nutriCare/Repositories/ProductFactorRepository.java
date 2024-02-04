package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFactorRepository extends JpaRepository<ProductFactor, Long> {
    @Query("SELECT pf.product, COUNT(DISTINCT pf.numeFactor) as factorCount " +
            "FROM ProductFactor pf " +
            "WHERE pf.numeFactor IN :factors " +
            "GROUP BY pf.product " +
            "HAVING COUNT(DISTINCT pf.numeFactor) = :factorCount")
    List<Product> findProductsByAllFactors(@Param("factors") List<String> factors,
                                           @Param("factorCount") long factorCount);
}
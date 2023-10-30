package com.example.nutriCare.Repositories;


import com.example.nutriCare.Entities.Factor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@EntityScan
@Repository
public interface FactorRepository extends JpaRepository<Factor,Long> {
}

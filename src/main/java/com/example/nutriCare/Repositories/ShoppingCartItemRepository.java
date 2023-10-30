package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.ShoppingCart;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@EntityScan
@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCart,Long> {

}

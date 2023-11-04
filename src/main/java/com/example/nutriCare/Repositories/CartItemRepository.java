package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
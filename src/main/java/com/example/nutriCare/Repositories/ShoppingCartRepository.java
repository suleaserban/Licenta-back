package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.ShoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc " +
            "JOIN FETCH sc.cartItems ci " +
            "JOIN FETCH ci.product p " +
            "WHERE sc.user.id = :userId " +
            "ORDER BY p.id ASC")
    Optional<ShoppingCart> findShoppingCartWithItemsSortedByProductId(@Param("userId") Long userId);

    Optional<ShoppingCart> findShoppingCartByUserId(@Param("userId") Long userId);

}
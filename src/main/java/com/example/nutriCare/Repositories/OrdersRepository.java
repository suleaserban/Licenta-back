package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}

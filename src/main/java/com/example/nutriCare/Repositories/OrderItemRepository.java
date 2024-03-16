package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

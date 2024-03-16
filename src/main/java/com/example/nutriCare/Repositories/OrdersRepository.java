package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {


}

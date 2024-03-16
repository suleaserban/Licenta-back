package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.OrderDTO;
import com.example.nutriCare.Dtos.OrderDetailsDTO;
import com.example.nutriCare.Entities.Order;
import com.example.nutriCare.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable Long userId , @RequestBody OrderDetailsDTO orderDetailsDTO) {
        try {
            OrderDTO orderDTO = orderService.placeOrder(userId,orderDetailsDTO);
            return ResponseEntity.ok(orderDTO);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
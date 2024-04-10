package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.OrderDTO;
import com.example.nutriCare.Dtos.OrderDetailsDTO;
import com.example.nutriCare.Dtos.OrderDisplayDTO;
import com.example.nutriCare.Entities.Order;
import com.example.nutriCare.Entities.OrderStatus;
import com.example.nutriCare.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDisplayDTO>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderDisplayDTO> orders = orderService.getOrdersByUserId(userId);
            if(orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all-orders")
    public ResponseEntity<List<OrderDisplayDTO>> getAllOrders() {
        try {
            List<OrderDisplayDTO> orders = orderService.getAllOrders();
            if(orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestParam("status") OrderStatus status) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
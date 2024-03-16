package com.example.nutriCare.Dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long userId;
    private LocalDateTime orderDate;
    private Double total;
    private List<OrderItemDTO> orderItems;


}
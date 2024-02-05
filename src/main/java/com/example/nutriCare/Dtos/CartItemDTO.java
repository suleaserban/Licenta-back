package com.example.nutriCare.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {

    private Long productId;
    private String productName;
    private int quantity;
    private Integer price;

}

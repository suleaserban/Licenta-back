package com.example.nutriCare.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Double valoare;
}

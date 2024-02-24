package com.example.nutriCare.Dtos;

import lombok.Data;

@Data
public class ScoreCalculationRequest {

        private PonderiDTO ponderiDto;
        private Boolean isVegan;

}

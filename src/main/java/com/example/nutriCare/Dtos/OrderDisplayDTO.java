package com.example.nutriCare.Dtos;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDisplayDTO {
    private Long id;
    private Date date;
    private String productNames;
    private String status;
    private double total;
    private String adress;
}

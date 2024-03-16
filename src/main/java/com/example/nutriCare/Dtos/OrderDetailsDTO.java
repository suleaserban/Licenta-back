package com.example.nutriCare.Dtos;

import lombok.Data;

@Data
public class OrderDetailsDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String county;
    private String codPostal;
    private String paymentMethod;

}
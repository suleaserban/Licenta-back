package com.example.nutriCare.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentsDTO {

    private Long doctorId;
    private Long userId;
    private LocalDateTime dataProgramare;
    private String status;
    private String sumar;
    private String doctorNume;
    private String userNume;
}

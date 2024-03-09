package com.example.nutriCare.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentsDTO {

    private Long doctorId;
    private Long userId;
    private LocalDateTime appointmentDate;
    private String status;
    private String summary;
    private String doctorNume;
    private String userNume;
    private String link;
}

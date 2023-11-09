package com.example.nutriCare.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String parola;
    private String rol;
    private String nume;
    private Integer varsta;
}

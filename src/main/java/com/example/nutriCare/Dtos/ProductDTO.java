package com.example.nutriCare.Dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String nume;
    private String descriere;
    private String beneficii;
    private String ingrediente;
    private String mod_administrare;
    private String contra_indicatii;
    private String producator;
    private Integer pret;
}

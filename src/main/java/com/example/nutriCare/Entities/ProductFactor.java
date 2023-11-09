package com.example.nutriCare.Entities;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "product_factors")
public class ProductFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String numeFactor;
    private Double valoare;


    public ProductFactor(String numeFactor, Double valoare) {
        this.numeFactor = numeFactor;
        this.valoare = valoare;
    }






}

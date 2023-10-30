package com.example.nutriCare.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;
    private String name;
    private String description;


    @OneToMany(mappedBy = "product")
    private List<Factor> factors;


    public double getFactorValue(String factorName) {
        for (Factor factor : factors) {
            if (factor.getName().equals(factorName)) {
                return factor.getValoareFactor();
            }
        }
        return 0.0;
    }
}

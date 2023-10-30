package com.example.nutriCare.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;




@Getter
@Setter
@NoArgsConstructor

@Entity
public class Factor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double valoareFactor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;



}
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

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String email;
    private String password;
    private String role;
    private Integer varsta;
    private Integer greutate;


    @OneToMany(mappedBy = "user")
    private List<UserProductScore> userProductScores;

}

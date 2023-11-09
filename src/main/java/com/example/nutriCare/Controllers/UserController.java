package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.PonderiDto;
import com.example.nutriCare.Dtos.UserDTO;
import com.example.nutriCare.Dtos.UserScoreDTO;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Entities.UserScore;
import com.example.nutriCare.Services.ProductFactorService;
import com.example.nutriCare.Services.ScoringService;
import com.example.nutriCare.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final ScoringService scoringService;
    private final UserService userService;
    private final ProductFactorService productFactorService;

    @Autowired
    public UserController(UserService userService, ProductFactorService productFactorService,ScoringService scoringService) {
        this.userService = userService;
        this.productFactorService = productFactorService;
        this.scoringService = scoringService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        User newUser = userService.createUser(userDTO);
        productFactorService.initializeUserScores(newUser);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/calculate-scores")
    public ResponseEntity<?> calculateScores(
            @PathVariable Long userId,
            @RequestBody PonderiDto ponderiDto
    ) {
        scoringService.calculateScoresForUser(userId, ponderiDto);
        return ResponseEntity.ok().build();
    }



}

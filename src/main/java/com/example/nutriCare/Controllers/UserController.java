package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.PonderiDTO;
import com.example.nutriCare.Dtos.UserDTO;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Services.ProductFactorService;
import com.example.nutriCare.Services.ScoringService;
import com.example.nutriCare.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


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

    @PostMapping("/{id}/calculate-scores")
    public ResponseEntity<?> calculateScores(
            @PathVariable Long id,
            @RequestBody PonderiDTO ponderiDto
    ) {
        logger.info("Calculating scores for id: {}", id);
        logger.info("Ponderi dto: {}", ponderiDto);
        scoringService.calculateScoresForUser(id, ponderiDto);
        logger.info("Scores calculated successfully for userId: {}", id);
        return ResponseEntity.ok().build();
    }



}

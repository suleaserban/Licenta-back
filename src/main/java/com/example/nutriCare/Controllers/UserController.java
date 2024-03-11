package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.DoctorDetailsDTO;
import com.example.nutriCare.Dtos.PonderiDTO;
import com.example.nutriCare.Dtos.ScoreCalculationRequest;
import com.example.nutriCare.Dtos.UserDTO;
import com.example.nutriCare.Entities.Role;
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

import java.util.List;

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
            @RequestBody ScoreCalculationRequest request
    ) {
        logger.info("Calculating scores for id: {}", id);
        logger.info("Ponderi dto: {}", request.getPonderiDto());
        scoringService.calculateScoresForUser(id, request.getPonderiDto(),request.getIsVegan());
        logger.info("Scores calculated successfully for userId: {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-doctors")
    public List<DoctorDetailsDTO> getAllDoctors() {
        return userService.findAllDoctors();
    }
    @GetMapping("/get-role-by-id/{id}")
    public ResponseEntity<Role> getUserRoleById(@PathVariable Long id) {
        Role userRole = userService.getUserRoleById(id);
        return new ResponseEntity<>(userRole, HttpStatus.OK);
    }

}

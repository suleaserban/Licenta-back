package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.UserScoreDTO;
import com.example.nutriCare.Services.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userScore")
public class UserScoreController {

    private final UserScoreService userScoresService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService) {
        this.userScoresService = userScoreService;
    }

    @GetMapping("/users/{userId}/top-scores")
    public ResponseEntity<List<UserScoreDTO>> getTopUserScores(@PathVariable Long userId) {
        List<UserScoreDTO> topScoresDTOs = userScoresService.findTopScoresByUserId(userId);
        return ResponseEntity.ok(topScoresDTOs);
    }

}
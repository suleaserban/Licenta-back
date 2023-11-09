package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.UserScoreDTO;
import com.example.nutriCare.Entities.UserScore;
import com.example.nutriCare.Repositories.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserScoreService {

    private final UserScoreRepository userScoreRepository;

    @Autowired
    public UserScoreService(UserScoreRepository userScoreRepository) {
        this.userScoreRepository = userScoreRepository;
    }

    public List<UserScoreDTO> findTopScoresByUserId(Long userId) {
        PageRequest pageRequest = PageRequest.of(0, 4);

        Page<UserScore> topScores = userScoreRepository.findByUserIdOrderByValoareDesc(userId, pageRequest);

        return topScores.stream()
                .map(score -> new UserScoreDTO(score.getId(),
                        score.getUser().getId(),
                        score.getProduct().getId(),
                        score.getValoare()))
                .collect(Collectors.toList());
    }

}
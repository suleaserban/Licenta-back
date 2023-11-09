package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.PonderiDto;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Entities.UserScore;
import com.example.nutriCare.Repositories.ProductRepository;
import com.example.nutriCare.Repositories.UserRepository;
import com.example.nutriCare.Repositories.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserScoreRepository userScoreRepository;

    public void calculateScoresForUser(Long userId, PonderiDto ponderiDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            double score = 0;

            for (ProductFactor factor : product.getProductFactors()) {

                Double pondere = ponderiDto.getPonderi().get(factor.getNumeFactor());

                if (pondere != null) {
                    score += factor.getValoare() * pondere;
                }
            }

            UserScore userScore = userScoreRepository
                    .findByUserIdAndProductId(userId, product.getId())
                    .orElse(new UserScore(user, product, 0.0));

            userScore.setValoare(score);
            userScoreRepository.save(userScore);
        }
    }


}

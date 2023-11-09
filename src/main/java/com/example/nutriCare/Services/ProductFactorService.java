package com.example.nutriCare.Services;

import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Entities.UserScore;
import com.example.nutriCare.Repositories.ProductRepository;
import com.example.nutriCare.Repositories.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFactorService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserScoreRepository userScoreRepository;
    public void initializeUserScores(User user) {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            UserScore userScore = new UserScore();
            userScore.setUser(user);
            userScore.setProduct(product);
            userScore.setValoare(0.0);
            userScoreRepository.save(userScore);
        }
    }

}

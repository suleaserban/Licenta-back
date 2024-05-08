package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.PonderiDTO;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Entities.UserScore;
import com.example.nutriCare.Repositories.ProductRepository;
import com.example.nutriCare.Repositories.UserRepository;
import com.example.nutriCare.Repositories.UserScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {

    @Mock
    private UserScoreRepository userScoreRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ScoringService scoringService;



    @Test
    void shouldSwapScoresIfVegan() {

        User user = new User();
        user.setId(1L);

        Product nonVeganProduct = new Product();
        nonVeganProduct.setId(10L);
        nonVeganProduct.setProductFactors(new HashSet<>());

        Product veganProduct = new Product();

        veganProduct.setId(21L);
        veganProduct.setProductFactors(new HashSet<>());

        HashMap<String, Double> ponderi = new HashMap<>();
        ponderi.put("factor_somnn", 1.5);
        ponderi.put("factor_soare", 1.0);
        PonderiDTO ponderiDto = new PonderiDTO();

        ponderiDto.setPonderi(ponderi);

        UserScore normalUserScore = new UserScore(user, nonVeganProduct, 2.0);
        UserScore veganUserScore = new UserScore(user, veganProduct, 0.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findAll()).thenReturn(Arrays.asList(nonVeganProduct, veganProduct));
        when(userScoreRepository.findByUserIdAndProductId(1L, 10L)).thenReturn(Optional.of(normalUserScore));
        when(userScoreRepository.findByUserIdAndProductId(1L, 21L)).thenReturn(Optional.of(veganUserScore));

        scoringService.calculateScoresForUser(1L, ponderiDto, true);

        verify(userScoreRepository, times(4)).save(any(UserScore.class));
    }


}
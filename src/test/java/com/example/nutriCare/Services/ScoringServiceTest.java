package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.PonderiDTO;
import com.example.nutriCare.Entities.Product;
import com.example.nutriCare.Entities.ProductFactor;
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
    void shouldCalculateScore() {

        User user = new User();
        user.setId(1L);

        Product melatonina = new Product();
        melatonina.setId(1L);
        Set<ProductFactor> factor_somn = new HashSet<>();
        factor_somn.add(new ProductFactor("factor_somn",2D));
        melatonina.setProductFactors(factor_somn);

        Product vitamina_d = new Product();

        vitamina_d.setId(2L);
        Set<ProductFactor> factor_soare = new HashSet<>();
        factor_soare.add(new ProductFactor("factor_soare",2D));

        vitamina_d.setProductFactors(factor_soare);

        HashMap<String, Double> ponderi = new HashMap<>();
        ponderi.put("factor_somn", 1.0);
        ponderi.put("factor_soare", 0.5);
        PonderiDTO ponderiDto = new PonderiDTO();
        ponderiDto.setPonderi(ponderi);

        UserScore expectedMelatoninaUserScore = new UserScore(user,melatonina,0.0);
        UserScore expectedVitaminaDUserScore = new UserScore(user,vitamina_d,0.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findAll()).thenReturn(Arrays.asList(melatonina, vitamina_d));
        when(userScoreRepository.findByUserIdAndProductId(1L, 1L)).thenReturn(Optional.of(expectedMelatoninaUserScore));
        when(userScoreRepository.findByUserIdAndProductId(1L, 2L)).thenReturn(Optional.of(expectedVitaminaDUserScore));

        scoringService.calculateScoresForUser(1L, ponderiDto, false);

        assertEquals(2D, expectedMelatoninaUserScore.getValoare());
        assertEquals(1D, expectedVitaminaDUserScore.getValoare());
    }


}
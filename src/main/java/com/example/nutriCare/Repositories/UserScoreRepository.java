package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.UserScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    Optional<UserScore> findByUserIdAndProductId(Long userId, Long productId);

    Page<UserScore> findByUserIdOrderByValoareDesc(Long userId, Pageable pageable);
}
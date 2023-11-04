package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {

}
package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.UserDTO;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setParola(userDTO.getParola());

        userRepository.save(user);
        return user;
    }

    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}

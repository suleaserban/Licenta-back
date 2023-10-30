package com.example.nutriCare.ServiceImpl;

import com.example.nutriCare.Entities.Users;
import com.example.nutriCare.Repositories.UserRepository;
import com.example.nutriCare.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UsersService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users createUser(Users user) {

        return userRepository.save(user);
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}

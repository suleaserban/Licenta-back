package com.example.nutriCare.Services;

import com.example.nutriCare.Entities.Users;
import com.example.nutriCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UsersService {
    Users createUser(Users user);

    Users getUserById(Long id);
}

package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.DoctorDetailsDTO;
import com.example.nutriCare.Dtos.UserDTO;
import com.example.nutriCare.Entities.Role;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Role getUserRoleById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRol();
    }

    public List<DoctorDetailsDTO> findAllDoctors() {
        List<User> allDoctors = userRepository.findByRol(Role.DOCTOR);
        return allDoctors.stream()
                .map(this::convertToDoctorDTO)
                .collect(Collectors.toList());
    }

    private DoctorDetailsDTO convertToDoctorDTO(User user) {
        DoctorDetailsDTO dto = new DoctorDetailsDTO();
        dto.setId(user.getId());
        dto.setNume(user.getNume());
        dto.setPrenume(user.getPrenume());

        return dto;
    }
}

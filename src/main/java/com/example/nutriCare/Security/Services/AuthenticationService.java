package com.example.nutriCare.Security.Services;

import com.example.nutriCare.Entities.Role;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Repositories.UserRepository;
import com.example.nutriCare.Security.AuthenticationRequest;
import com.example.nutriCare.Security.AuthenticationResponse;
import com.example.nutriCare.Security.JwtService;
import com.example.nutriCare.Security.RegisterRequest;
import com.example.nutriCare.Services.ProductFactorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        private final JwtService jwtService;

        private final AuthenticationManager authenticationManager;

        private final ProductFactorService productFactorService;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setVarsta(request.getVarsta());
        user.setParola(passwordEncoder.encode(request.getParola()));
        user.setRol(Role.USER);

        userRepository.save(user);

        productFactorService.initializeUserScores(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getParola()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            var jwtToken = jwtService.generateToken(user);

            logger.info("Autentificare reușită pentru utilizatorul: {}", request.getEmail());

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            logger.error("Eroare la autentificare pentru utilizatorul: {}", request.getEmail(), e);
            throw e; // sau gestionați excepția după cum este necesar
        }
    }
}

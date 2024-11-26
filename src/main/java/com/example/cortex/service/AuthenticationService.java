package com.example.cortex.service;

import com.example.cortex.dto.response.AuthenticationResponse;
import com.example.cortex.dto.request.LoginRequest;
import com.example.cortex.dto.request.RegisterRequest;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.User;
import com.example.cortex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        validateRegisterRequest(request);

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("Usuário já registrado com este email");
        }

        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        var jwtToken = generateJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }

    public AuthenticationResponse login(@NotNull LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CustomException("Email ou senha inválidos");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Usuário não encontrado"));

        var jwtToken = generateJwtToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new CustomException("Todos os campos são necessários");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(request.getEmail()).matches()) {
            throw new CustomException("Email inválido");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new CustomException(
                    "Senha deve ter pelo menos 8 caracteres, incluindo um número e um caractere especial.");
        }
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}";
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private String generateJwtToken(User user) {
        return jwtService.generateToken(user.getUserId(), user.getUsername(), user.getEmail());
    }
}

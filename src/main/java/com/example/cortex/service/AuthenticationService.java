package com.example.cortex.service;

import com.example.cortex.dto.AuthenticationResponse;
import com.example.cortex.dto.LoginRequest;
import com.example.cortex.dto.RegisterRequest;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.User;
import com.example.cortex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

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

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("Usuário já registrado com este email");
        }


        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .build();


        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user.getUserId(),user.getUsername(), user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        try{
         authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        }catch(Exception e){
            throw new CustomException("Email ou senha inválidos");
            }
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getUserId(), user.getUsername(),user.getEmail());
        return AuthenticationResponse.builder().token(jwtToken).username(user.getUsername()).build();
    }
}

package com.example.cortex.service;

import com.example.cortex.exception.CustomException;
import com.example.cortex.model.Project;
import com.example.cortex.model.User;
import com.example.cortex.repository.ProjectUserRepository;
import com.example.cortex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário: " + userEmail + " não encontrado"));


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }



    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new CustomException("Usuário não encontrado"));
    }

    public void validateUserAccess(Project project) {
        User currentUser = getCurrentUser();
        if (!projectUserRepository.existsByProjectAndUser(project, currentUser)) {
            throw new CustomException("Acesso negado");
        }
    }


}

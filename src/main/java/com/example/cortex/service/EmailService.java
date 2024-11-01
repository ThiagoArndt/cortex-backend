package com.example.cortex.service;

import com.example.cortex.dto.request.ProjectInviteRequest;
import com.example.cortex.exception.CustomException;
import com.example.cortex.model.Project;
import com.example.cortex.model.User;
import com.example.cortex.repository.ProjectUserRepository;
import com.example.cortex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final ProjectService projectService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProjectUserRepository projectUserRepository;
    private final JwtService jwtService;

    @Value("${frontend.url}")
    private String frontendUrl;
    private void sendInvitationEmail(String email, String projectName, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Convite de Projeto");
        message.setText("Você foi convidado a entrar no projeto: " + projectName + "\n" +
                "Clique aqui para aceitar:"+frontendUrl+"/accept-invitation?token=" + token);
        mailSender.send(message);
    }

    public void inviteUser(ProjectInviteRequest request) {
        Project project = projectService.getProjectById(request.getProjectId());
        userService.validateUserAccess(project);

        User invitedUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("Usuário não encontrado"));

        if (projectUserRepository.existsByProjectAndUser(project, invitedUser)) {
            throw new CustomException("Usuário já possui acesso a este projeto");
        }

        String token = jwtService.generateInvitationToken(invitedUser.getEmail(), project.getProjectId());
        sendInvitationEmail(invitedUser.getEmail(), project.getProjectName(), token);
    }

    public  void acceptInvite(String token) {
        if (!jwtService.validateToken(token) || !jwtService.isInvitationToken(token)) {
            return;
        }


        String email = jwtService.extractUsername(token);
        Integer projectId = jwtService.extractProjectId(token);


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Usuário não registrado"));


        Project project = projectService.getProjectById(projectId);


        if (projectUserRepository.existsByProjectAndUser(project, user)) {
            throw new CustomException("Usuário já tem acesso a este projeto");
        }

        projectService.addUserToProject(user, project);
    }
}

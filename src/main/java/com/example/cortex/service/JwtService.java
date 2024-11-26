package com.example.cortex.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private String generateToken(String type, Integer userId, String username, String email, Integer projectId) {
        var jwtBuilder = JWT.create()
                .withClaim("type", type)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + getExpirationTime(type)));

        if ("authentication".equals(type)) {
            jwtBuilder.withSubject(username)
                    .withClaim("user_id", userId)
                    .withClaim("email", email);
        } else if ("invitation".equals(type)) {
            jwtBuilder.withClaim("email", email)
                    .withClaim("projectId", projectId);
        }

        return jwtBuilder.sign(Algorithm.HMAC256(secret));
    }

    public String generateToken(Integer userId, String username, String email) {
        return generateToken("authentication", userId, username, email, null);
    }

    public String generateInvitationToken(String email, Integer projectId) {
        return generateToken("invitation", null, null, email, projectId);
    }

    private long getExpirationTime(String type) {
        if ("invitation".equals(type)) {
            return 1000L * 60 * 60 * 24 * 7; // 7 dias para convite
        } else {
            return 1000L * 60 * 60 * 10; // 10 horas para autenticação
        }
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .acceptExpiresAt(0)
                    .build()
                    .verify(token);

            return !isTokenExpired(decodedJWT);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, "email");
    }

    public Integer extractProjectId(String token) {
        return extractClaim(token, "projectId");
    }

    private <T> T extractClaim(String token, String claim) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(claim).as(claim.equals("projectId") ? Integer.class : String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isInvitationToken(String token) {
        return "invitation".equals(extractClaim(token, "type"));
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        Date expirationDate = decodedJWT.getExpiresAt();
        return expirationDate != null && expirationDate.before(new Date());
    }
}

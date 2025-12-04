package com.github.carlossnogueira.inventorylite.api.application.service.User;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.carlossnogueira.inventorylite.api.exception.UserException.EmailOrPasswordIncorrectException;
import com.github.carlossnogueira.inventorylite.domain.dto.request.AuthenticateUserJson;
import com.github.carlossnogueira.inventorylite.domain.entities.User;
import com.github.carlossnogueira.inventorylite.domain.repositories.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    @Value("${security.token.secret}")
    private String secretKey;

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(AuthenticateUserJson request) {

        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailOrPasswordIncorrectException());

        var passwordMatches = this.passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new EmailOrPasswordIncorrectException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("inventorylite")
                .withSubject(user.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(7L)))
                .sign(algorithm);

        return token;
    }

}

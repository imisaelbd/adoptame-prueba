package mx.edu.utez.adoptameappserver.service.auth;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.model.user.UserRepository;
import mx.edu.utez.adoptameappserver.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository repository;

    private final AuthenticationManager manager;

    private final JwtProvider provider;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> create (User user) {
        String id = UUID.randomUUID().toString();
        String newId = id.substring(0, 7);
        user.setId(newId);
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = repository.save(user);
        return new ApiResponse<>(
                newUser, false, HttpStatus.OK, "Usuario registrado"
        );
    }


}

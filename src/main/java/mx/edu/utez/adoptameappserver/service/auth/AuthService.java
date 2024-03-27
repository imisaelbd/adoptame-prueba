package mx.edu.utez.adoptameappserver.service.auth;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.controller.auth.dto.SignDto;
import mx.edu.utez.adoptameappserver.controller.auth.dto.SignedDto;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.model.user.UserRepository;
import mx.edu.utez.adoptameappserver.controller.user.dto.UserDto;
import mx.edu.utez.adoptameappserver.security.jwt.JwtProvider;
import mx.edu.utez.adoptameappserver.security.service.UserDetailsServiceImpl;
import mx.edu.utez.adoptameappserver.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository repository;

    private final AuthenticationManager manager;

    private final JwtProvider provider;

    private final PasswordEncoder passwordEncoder;

    private final UserService service;

    private final UserDetailsServiceImpl userDetailsService;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> create (UserDto user) {
        User newUser = new User();
        String id = UUID.randomUUID().toString();
        String newId = id.substring(0, 7);
        newUser.setId(newId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, newUser);
        newUser.setBlocked(true);
        newUser.setStatus(true);
        User saveUser = repository.save(newUser);
        return new ApiResponse<>(
                saveUser, false, HttpStatus.OK, "hola"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<SignedDto> signIn (SignDto user) {
        try {
            Optional<User> foundUser = service.findByEmail(user.getEmail());
            if (foundUser.isEmpty())
                return new ApiResponse<>(
                        null, true, HttpStatus.NOT_FOUND, "Usuario no encontrado"
                );
            User userFound = foundUser.get();
            if (Boolean.FALSE.equals(userFound.getStatus()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED, "Usuario inactivo"
                );
            if (Boolean.FALSE.equals(userFound.getBlocked()))
                return new ApiResponse<>(
                        null, true, HttpStatus.UNAUTHORIZED, "Usuario bloqueado"
                );
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = provider.generateToken(userDetails);
            SignedDto signedDto = new SignedDto(token, userFound);
            return new ApiResponse<>(
                    signedDto, false, HttpStatus.OK, "Login correcto!"
            );
        } catch (Exception e) {
            String message = "Credenciales incorrectas";
            if (e instanceof DisabledException)
                message = "Usuario deshabilitado";
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, message
            );
        }
    }


}

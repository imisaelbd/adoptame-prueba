package mx.edu.utez.adoptameappserver.controller.auth;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-adopt/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService service;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> create (@RequestBody User user){
        try {
            ApiResponse<User> response = service.create(user);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(
                    response,
                    statusCode
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}

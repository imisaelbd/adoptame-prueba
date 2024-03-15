package mx.edu.utez.adoptameappserver.controller.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api-adopt/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class UserController {

    private final UserService service;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<User>>> getAll () {
        try {
            ApiResponse<List<User>> response = service.getAll();
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

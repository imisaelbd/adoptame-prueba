package mx.edu.utez.adoptameappserver.controller.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.edu.utez.adoptameappserver.model.user.User;

@Data
@AllArgsConstructor

public class SignedDto {
    String token;
    User user;
}

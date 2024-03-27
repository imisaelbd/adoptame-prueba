package mx.edu.utez.adoptameappserver.controller.user.dto;

import lombok.Data;
import mx.edu.utez.adoptameappserver.model.role.Role;

import java.util.Date;

@Data

public class UserDto {

    private String fullName;

    private String email;

    private String password;

    private String phone;

    private String address;

    private Date birthday;

    private Role role;
}

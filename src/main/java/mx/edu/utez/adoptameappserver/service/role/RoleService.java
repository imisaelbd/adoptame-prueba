package mx.edu.utez.adoptameappserver.service.role;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.model.role.Role;
import mx.edu.utez.adoptameappserver.model.role.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class RoleService {

    private final RoleRepository repository;

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<Role> create (Role role) {
        String id = UUID.randomUUID().toString();
        String newId = id.substring(0, 7);
        role.setId(newId);
        Role newRole = repository.save(role);
        return new ApiResponse<>(
                newRole, false, HttpStatus.OK, "Rol registrado correctamente"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<Role>> getAll () {
        List<Role> roles = repository.findAll();
        if (roles.isEmpty()) {
            return new ApiResponse<>(
                    roles, false ,HttpStatus.BAD_REQUEST, "No hay roles registrados"
            );
        } else {
            return new ApiResponse<>(
                    roles, false, HttpStatus.OK, "Roles encontrados"
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<Role> getById (String id) {
        Optional<Role> optionalRole = repository.findById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return new ApiResponse<>(
                    role, false, HttpStatus.OK, "Rol encontrado"
            );
        } else {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Rol no encontrado"
            );
        }
    }
}

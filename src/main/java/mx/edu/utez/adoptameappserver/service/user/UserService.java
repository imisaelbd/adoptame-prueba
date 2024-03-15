package mx.edu.utez.adoptameappserver.service.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.adoptameappserver.config.ApiResponse;
import mx.edu.utez.adoptameappserver.model.user.User;
import mx.edu.utez.adoptameappserver.model.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class UserService {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public ApiResponse<List<User>> getAll () {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            return new ApiResponse<>(
                    users, false ,HttpStatus.BAD_REQUEST, "No hay usuarios registrados"
            );
        } else {
            return new ApiResponse<>(
                    users, false, HttpStatus.OK, "Usuarios encontrados"
            );
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> findById(String id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse<>(
                    user, false, HttpStatus.OK, "Usuario encontrado"
            );
        } else {
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario no encontrado"
            );
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ApiResponse<User> deleteById(String id) {
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty())
            return new ApiResponse<>(
                    null, true, HttpStatus.BAD_REQUEST, "Usuario no encontrado"
            );
        User deletedUser = foundUser.get();
        repository.deleteById(id);
        return new ApiResponse<>(
                deletedUser, false, HttpStatus.OK, "Usuario eliminado"
        );
    }

}

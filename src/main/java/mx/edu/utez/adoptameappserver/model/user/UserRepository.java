package mx.edu.utez.adoptameappserver.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail (String email);

    @Modifying
    @Query(value = "INSERT INTO user_roles(user_id, role_id) " +
            "VALUES ( :userId, :roleId )", nativeQuery = true)
    int saveUserRole(String userId, String roleId);

    @Query(value = "SELECT user_id FROM user_roles WHERE user_id = :userId AND " +
            "role_id = :roleId ", nativeQuery = true)
    String getIdUserRoles(String userId, String roleId);
}

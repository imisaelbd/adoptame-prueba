package mx.edu.utez.adoptameappserver.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.adoptameappserver.model.role.Role;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    private String id;

    @Column(length = 50, nullable = false)
    private String fullname;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 10, nullable = false)
    private String phone;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date birthday;

    @Column(length = 150, nullable = false)
    private String address;

//    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime createdAt;

    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean status;

    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean blocked;

    private String token;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private Set<Role> role;
}

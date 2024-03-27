package mx.edu.utez.adoptameappserver.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.adoptameappserver.model.role.Role;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor

public class User {
    @Id
    private String id;

    @Column(length = 50, nullable = false)
    private String fullName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 150, nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 10, nullable = false)
    private String phone;

    @Column(columnDefinition = "DATE", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    private LocalDate createdAt;

    @Column(columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;

    @Column(columnDefinition = "BOOL DEFAULT true")
    private Boolean blocked;

    private String token;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

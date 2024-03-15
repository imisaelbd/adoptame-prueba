package mx.edu.utez.adoptameappserver.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse <T> {

    private T data;

    private boolean error;

    private HttpStatus status;

    String message;
}

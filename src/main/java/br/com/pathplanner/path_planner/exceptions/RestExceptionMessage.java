package br.com.pathplanner.path_planner.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestExceptionMessage {
    private HttpStatus status;
    private String message;


}

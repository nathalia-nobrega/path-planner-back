package br.com.pathplanner.path_planner.exceptions.advices;

import br.com.pathplanner.path_planner.exceptions.RestExceptionMessage;
import br.com.pathplanner.path_planner.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UserAlreadyExistsExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<RestExceptionMessage> userAlreadyExistsHandler(UserAlreadyExistsException ex) {
        RestExceptionMessage response = new RestExceptionMessage(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

package br.com.pathplanner.path_planner.exceptions.advices;

import br.com.pathplanner.path_planner.exceptions.RestExceptionMessage;
import br.com.pathplanner.path_planner.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class UserNotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<RestExceptionMessage> userNotFoundHandler(UserNotFoundException ex) {
        RestExceptionMessage response = new RestExceptionMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

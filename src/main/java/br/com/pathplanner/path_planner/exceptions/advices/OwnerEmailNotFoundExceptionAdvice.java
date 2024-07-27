package br.com.pathplanner.path_planner.exceptions.advices;

import br.com.pathplanner.path_planner.exceptions.OwnerEmailNotFoundException;
import br.com.pathplanner.path_planner.exceptions.RestExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class OwnerEmailNotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(OwnerEmailNotFoundException.class)
    ResponseEntity<RestExceptionMessage> ownerEmailNotFoundHandler(OwnerEmailNotFoundException ex) {
        RestExceptionMessage response = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

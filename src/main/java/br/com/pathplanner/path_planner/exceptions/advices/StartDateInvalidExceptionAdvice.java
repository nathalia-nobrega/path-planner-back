package br.com.pathplanner.path_planner.exceptions.advices;

import br.com.pathplanner.path_planner.exceptions.RestExceptionMessage;
import br.com.pathplanner.path_planner.exceptions.StartDateInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class StartDateInvalidExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(StartDateInvalidException.class)
    ResponseEntity<RestExceptionMessage> startDateInvalidHandler(StartDateInvalidException ex) {
        RestExceptionMessage response = new RestExceptionMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}

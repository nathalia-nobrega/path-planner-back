package br.com.pathplanner.path_planner.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

     public UserAlreadyExistsException() {
        super("Este e-mail já foi cadastrado!");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}

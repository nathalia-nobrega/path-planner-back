package br.com.pathplanner.path_planner.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("O usuário informado não existe.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

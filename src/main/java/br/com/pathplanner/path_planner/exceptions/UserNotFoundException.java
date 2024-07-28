package br.com.pathplanner.path_planner.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("O usuário informado não existe ou os dados são incorretos.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}

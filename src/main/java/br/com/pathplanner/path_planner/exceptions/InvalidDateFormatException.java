package br.com.pathplanner.path_planner.exceptions;

public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException() {
        super("A data informada não é válida.");
    }
}

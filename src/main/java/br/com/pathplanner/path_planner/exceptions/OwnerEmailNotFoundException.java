package br.com.pathplanner.path_planner.exceptions;

public class OwnerEmailNotFoundException extends RuntimeException {
    public OwnerEmailNotFoundException() {
        super("O e-mail informado para o criador da viagem não existe.");
    }

    public OwnerEmailNotFoundException(String message) {
        super(message);
    }
}

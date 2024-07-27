package br.com.pathplanner.path_planner.exceptions;

import java.time.LocalDateTime;

public class StartDateInvalidException extends RuntimeException {
    public StartDateInvalidException() {
        super("A data de início deve ser inferior à data de término da viagem.");
    }
}

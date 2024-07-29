package br.com.pathplanner.path_planner.modules.trip;

import java.io.Serializable;

public record TripDto(String destination, String startsAt, String endsAt, String ownerName, String ownerEmail, boolean isConfirmed) implements Serializable {
}

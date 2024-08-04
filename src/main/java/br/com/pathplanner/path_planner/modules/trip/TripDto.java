package br.com.pathplanner.path_planner.modules.trip;

public record TripDto(String destination, String startsAt, String endsAt, String ownerName, String ownerEmail, boolean isConfirmed) {
}

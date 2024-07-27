package br.com.pathplanner.path_planner.modules.trip;

import java.util.List;

public record TripRequestPayload(String destination, String starts_at, String ends_at, String owner_email, String owner_name) {
}

package br.com.pathplanner.path_planner.modules.activity;


import br.com.pathplanner.path_planner.modules.trip.Trip;

public record ActivityRequestPayload(String title, String occurs_at, Trip trip) {
}

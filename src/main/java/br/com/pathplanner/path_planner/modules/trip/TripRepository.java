package br.com.pathplanner.path_planner.modules.trip;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, String> {
}

package br.com.pathplanner.path_planner.modules.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findAllByTripId(String id);
}

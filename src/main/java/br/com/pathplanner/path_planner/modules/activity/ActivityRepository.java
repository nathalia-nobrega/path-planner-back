package br.com.pathplanner.path_planner.modules.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Query("SELECT a FROM activities a WHERE a.trip.id = :tripId")
    List<Activity> findActivitiesByTrip(String tripId);

}

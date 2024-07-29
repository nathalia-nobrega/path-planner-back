package br.com.pathplanner.path_planner.modules.link;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, String> {
    List<Link> findAllByTripId(String id);
}

package br.com.pathplanner.path_planner.modules.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findAllByTripId(String id);
}

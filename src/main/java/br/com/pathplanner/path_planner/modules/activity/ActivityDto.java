package br.com.pathplanner.path_planner.modules.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityDto(UUID id, String title, String occurs_at) {
}

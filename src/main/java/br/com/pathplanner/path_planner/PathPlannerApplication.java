package br.com.pathplanner.path_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PathPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PathPlannerApplication.class, args);
	}

}

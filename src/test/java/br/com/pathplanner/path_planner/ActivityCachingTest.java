package br.com.pathplanner.path_planner;

import br.com.pathplanner.path_planner.modules.activity.Activity;
import br.com.pathplanner.path_planner.modules.activity.ActivityRepository;
import br.com.pathplanner.path_planner.modules.activity.ActivityService;
import br.com.pathplanner.path_planner.modules.trip.Trip;
import br.com.pathplanner.path_planner.modules.trip.TripRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ActivityCachingTest {

    @Autowired
    @InjectMocks
    private ActivityService service;

    @Mock
    @Autowired
    private ActivityRepository actRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    CacheManager cacheManager;

    private Activity act1;
    private Activity act2;
    private Trip tripRaw;

    @BeforeEach
    public void setUp() {
        tripRaw = new Trip("18f21da7-c35c-486d-a609-c37a5171789b", "França", LocalDateTime.parse("2024-08-25T21:51:54.7342"), LocalDateTime.parse("2024-08-26T21:51:54.7342"), false, "Nathalia Nóbrega", "ttn@gmail.com");
        tripRepository.save(tripRaw);

        act1 = new Activity("Ver Brasil x França",  "2024-08-26T21:51:54.7342", tripRaw);
        act2 = new Activity("Ir à torre",  "2024-08-25T21:51:54.7342", tripRaw);

        actRepository.save(act1);
        actRepository.save(act2);
    }

    @AfterEach
    public void tearDown() {
        act1 = act2 = null;
    }

    @Test
    void givenCallToGetAllActivitiesThenShouldReturnListOfAllActivities() {

        service.getAllActivitiesFromTrip(tripRaw.getId());
        service.getAllActivitiesFromTrip(tripRaw.getId());
        service.getAllActivitiesFromTrip(tripRaw.getId());
        service.getAllActivitiesFromTrip(tripRaw.getId());

    }
}

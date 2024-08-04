package br.com.pathplanner.path_planner.modules.activity;

import br.com.pathplanner.path_planner.modules.trip.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "activities")
public class ActivityService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ActivityRepository repository;

    @Caching(evict = {@CacheEvict(value = "allActivitiesCache", allEntries = true),
        @CacheEvict(value = "activityCache", key = "#payload.title()")
    })
    public ActivityCreateResponse registerActitivty(ActivityRequestPayload payload, Trip trip) {
        Activity act = new Activity(payload.title(), payload.occurs_at(), trip);
        this.repository.save(act);
        log.info("REPOSITORY - Create: Activity {} -- belongs to trip {} ->", act.getId(), act.getTrip().getId());

        return new ActivityCreateResponse(act.getId());
    }

    public ResponseEntity<ActivityDto> updateActivity(String activityId, ActivityRequestUpdatePayload payload) {
        Optional<Activity> actv = this.repository.findById(activityId);

        if (actv.isPresent()) {
            Activity activity = actv.get();
            activity.setTitle(payload.title());
            LocalDateTime dateTime = LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            activity.setOccursAt(dateTime);

            this.repository.save(activity);

            ActivityDto dtoResponse = new ActivityDto(activityId, activity.getTitle(), activity.getOccursAt().toString());

            return ResponseEntity.ok(dtoResponse);
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> deleteActivity(String activityId) {
        if (this.repository.findById(activityId).isPresent()) {
            this.repository.deleteById(activityId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Cacheable(value = "allActivitiesCache")
    public List<ActivityDto> getAllActivitiesFromTrip(String tripId) {
        System.out.println("Retrieving all the activities...");
        List<ActivityDto> list = this.repository.findActivitiesByTrip(tripId).stream().map(activity -> new ActivityDto(activity.getId(), activity.getTitle(), activity.getOccursAt().toString())).toList();
        return list;
    }
}

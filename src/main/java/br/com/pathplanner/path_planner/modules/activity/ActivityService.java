package br.com.pathplanner.path_planner.modules.activity;

import br.com.pathplanner.path_planner.modules.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityCreateResponse registerActitivty(ActivityRequestPayload payload, Trip trip) {
        Activity act = new Activity(payload.title(), payload.occurs_at(), trip);
        this.repository.save(act);
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
    public List<ActivityDto> getAllActivitiesFromTrip(String tripId) {
        List<ActivityDto> list = this.repository.findAllByTripId(tripId).stream().map(activity -> new ActivityDto(activity.getId(), activity.getTitle(), activity.getOccursAt().toString())).toList();
        return list;
    }
}

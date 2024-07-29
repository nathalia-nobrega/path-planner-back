package br.com.pathplanner.path_planner.modules.trip;

import br.com.pathplanner.path_planner.exceptions.InvalidDateFormatException;
import br.com.pathplanner.path_planner.exceptions.OwnerEmailNotFoundException;
import br.com.pathplanner.path_planner.exceptions.StartDateInvalidException;
import br.com.pathplanner.path_planner.modules.activity.ActivityCreateResponse;
import br.com.pathplanner.path_planner.modules.activity.ActivityRequestPayload;
import br.com.pathplanner.path_planner.modules.activity.ActivityService;
import br.com.pathplanner.path_planner.modules.items.ItemCreateResponse;
import br.com.pathplanner.path_planner.modules.items.ItemRequestPayload;
import br.com.pathplanner.path_planner.modules.items.ItemService;
import br.com.pathplanner.path_planner.modules.link.LinkCreateResponse;
import br.com.pathplanner.path_planner.modules.link.LinkRequestPayload;
import br.com.pathplanner.path_planner.modules.link.LinkService;
import br.com.pathplanner.path_planner.modules.user.User;
import br.com.pathplanner.path_planner.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

      public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) throws StartDateInvalidException {
          LocalDateTime start_date;
          LocalDateTime ends_date;

          try {
              start_date = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
              ends_date = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
          } catch (DateTimeParseException ex) {
              throw new InvalidDateFormatException();
          }

          if (start_date.isAfter(ends_date)) throw new StartDateInvalidException();

          Optional<User> userByEmail = this.userService.findUserByEmail(payload.owner_email());

          if(userByEmail.isEmpty()) throw new OwnerEmailNotFoundException();

          Trip newTrip = new Trip(payload);

          this.repository.save(newTrip);

          return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
      }

     public ResponseEntity<TripDto> getTripDetails(@PathVariable String id){
        Optional<Trip> trip = getTrip(id);

         if(trip.isPresent()) {
            Trip rawTrip = trip.get();
            TripDto tripDto = new TripDto(rawTrip.getDestination(),
                    rawTrip.getStartsAt().toString(),
                    rawTrip.getEndsAt().toString(),
                    rawTrip.getOwnerName(),
                    rawTrip.getOwnerEmail(),
                    rawTrip.getIsConfirmed());
            return ResponseEntity.ok(tripDto);
        }
        return ResponseEntity.notFound().build();
    }


    public ResponseEntity<TripDto> confirmTrip(@PathVariable String id){
        Optional<Trip> trip = getTrip(id);


        if (trip.isPresent()) {
            Trip rawTrip = trip.get();
            TripDto tripDto = new TripDto(rawTrip.getDestination(),
                    rawTrip.getOwnerEmail(),
                    rawTrip.getOwnerName(),
                    rawTrip.getStartsAt().toString(),
                    rawTrip.getStartsAt().toString(),
                    rawTrip.getIsConfirmed());

            rawTrip.setIsConfirmed(true);
            this.repository.save(rawTrip);

            return ResponseEntity.ok(tripDto);
        }

        return ResponseEntity.notFound().build();
    }

    // Tratar data da atividade dentro da data da viagem
    public ResponseEntity<ActivityCreateResponse> registerActivity(@PathVariable String id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = getTrip(id);

        if (trip.isPresent()) {
            ActivityCreateResponse response = this.activityService.registerActitivty(payload, trip.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<LinkCreateResponse> registerLink(@PathVariable String id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = getTrip(id);

        if (trip.isPresent()) {
            LinkCreateResponse response = this.linkService.registerLink(payload, trip.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

     public ResponseEntity<ItemCreateResponse> registerItem(@PathVariable String id, @RequestBody ItemRequestPayload payload) {
        Optional<Trip> trip = getTrip(id);

        if (trip.isPresent()) {
            ItemCreateResponse response = this.itemService.registerItem(payload, trip.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    private Optional<Trip> getTrip(String id) {
        return this.repository.findById(id);
    }


}

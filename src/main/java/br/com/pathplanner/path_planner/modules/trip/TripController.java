package br.com.pathplanner.path_planner.modules.trip;


import br.com.pathplanner.path_planner.exceptions.StartDateInvalidException;
import br.com.pathplanner.path_planner.modules.activity.*;
import br.com.pathplanner.path_planner.modules.items.ItemCreateResponse;
import br.com.pathplanner.path_planner.modules.items.ItemDto;
import br.com.pathplanner.path_planner.modules.items.ItemRequestPayload;
import br.com.pathplanner.path_planner.modules.items.ItemService;
import br.com.pathplanner.path_planner.modules.link.LinkCreateResponse;
import br.com.pathplanner.path_planner.modules.link.LinkDto;
import br.com.pathplanner.path_planner.modules.link.LinkRequestPayload;
import br.com.pathplanner.path_planner.modules.link.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TripService service;

    // TRIP CRUD

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) throws StartDateInvalidException {
        return this.service.createTrip(payload);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TripDto> getTripDetails(@PathVariable("id") UUID id){
        return this.service.getTripDetails(id);
    }

    @GetMapping(path = "/{id}/confirm")
    public ResponseEntity<TripDto> confirmTrip(@PathVariable UUID id){
        return this.service.confirmTrip(id);
    }


    // ACTIVITY CRUD
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityCreateResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        return this.service.registerActivity(id, payload);
    }

    @DeleteMapping("/{id}/activities/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable UUID activityId) {
        return this.activityService.deleteActivity(activityId);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDto>> getAllActivitiesByTripId(@PathVariable UUID id) {
        List<ActivityDto> allParticipantsFromTrip = this.activityService.getAllActivitiesFromTrip(id);
        return ResponseEntity.ok(allParticipantsFromTrip);
    }

    @PutMapping("/{id}/activities/{activityId}")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID activityId, @RequestBody ActivityRequestUpdatePayload payload) {
        return this.activityService.updateActivity(activityId, payload);
    }

    // LINKS CRUD
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkCreateResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        return this.service.registerLink(id, payload);
    }

    @PutMapping("/{id}/links/{linkId}")
    public ResponseEntity<LinkDto> updateLink(@PathVariable UUID linkId, @RequestBody LinkRequestPayload payload) {
        return this.linkService.updateLink(linkId, payload);
    }


    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDto>> getAllLinksByTripId(@PathVariable UUID id) {
        List<LinkDto> allLinksFromTrip = this.linkService.getAllLinksFromTrip(id);
        return ResponseEntity.ok(allLinksFromTrip);
    }

    // ITEMS CRUD
    @PostMapping("/{id}/items")
    public ResponseEntity<ItemCreateResponse> registerItem(@PathVariable UUID id, @RequestBody ItemRequestPayload payload) {
        return this.service.registerItem(id, payload);
    }


    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemDto>> getAllItemsByTripId(@PathVariable UUID id) {
        List<ItemDto> allLinksFromTrip = this.itemService.getAllItemsFromTrip(id);
        return ResponseEntity.ok(allLinksFromTrip);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID itemId) {
        return this.itemService.deleteItem(itemId);
    }




}

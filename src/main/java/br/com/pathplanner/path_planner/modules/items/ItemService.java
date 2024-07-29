package br.com.pathplanner.path_planner.modules.items;

import br.com.pathplanner.path_planner.modules.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public ItemCreateResponse registerItem(ItemRequestPayload payload, Trip trip) {
        Item item = new Item(payload.title(), trip);
        this.repository.save(item);
        return new ItemCreateResponse(item.getId());
    }

    public List<ItemDto> getAllItemsFromTrip(String tripId) {
        List<ItemDto> list = this.repository.findAllByTripId(tripId).stream().map(Item -> new ItemDto(Item.getId(), Item.getTitle())).toList();
        return list;
    }

    public ResponseEntity<Object> deleteItem(String itemId) {
        if (this.repository.findById(itemId).isPresent()) {
            this.repository.deleteById(itemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

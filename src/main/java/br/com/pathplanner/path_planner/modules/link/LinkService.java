package br.com.pathplanner.path_planner.modules.link;

import br.com.pathplanner.path_planner.modules.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkCreateResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link link = new Link(payload.url(), payload.title(), trip);
        this.repository.save(link);

        return new LinkCreateResponse(link.getId());
    }

    public ResponseEntity<LinkDto> updateLink(UUID linkId, LinkRequestPayload payload) {
        Optional<Link> link = this.repository.findById(linkId);

        if (link.isPresent()) {
            Link rawLink = link.get();

            rawLink.setTitle(payload.title());
            rawLink.setUrl(payload.url());

            this.repository.save(rawLink);

            LinkDto dtoResponse = new LinkDto(linkId, rawLink.getTitle(), rawLink.getUrl());

            return ResponseEntity.ok(dtoResponse);
        }

        return ResponseEntity.notFound().build();
    }


    public List<LinkDto> getAllLinksFromTrip(UUID tripId) {
        List<LinkDto> list = this.repository.findAllByTripId(tripId).stream().map(link -> new LinkDto(link.getId(), link.getTitle(), link.getUrl())).toList();
        return list;
    }
}

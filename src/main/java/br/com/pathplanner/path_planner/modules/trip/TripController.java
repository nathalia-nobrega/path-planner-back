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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@Tag(name = "Trip", description = "Informações da viagem")
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
    @Operation(summary = "Cadastro de viagem", description = "Essa função é responsável por cadastrar uma viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TripCreateResponse.class))
            }),
    })
    public ResponseEntity<TripCreateResponse> createTrip(@Valid @RequestBody TripRequestPayload payload) throws StartDateInvalidException {
        return this.service.createTrip(payload);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Detalhes da viagem", description = "Essa função é responsável por buscar os detalhes da viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TripDto.class))
            }),
             @ApiResponse(responseCode = "404", description = "Viagem não encontrada")
    })
    public ResponseEntity<TripDto> getTripDetails(@PathVariable("id") UUID id){
        return this.service.getTripDetails(id);
    }

    @GetMapping(path = "/{id}/confirm")
    @Operation(summary = "Confirmação da viagem", description = "Essa função é responsável por confirmar a viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = TripDto.class))
            }),
             @ApiResponse(responseCode = "404", description = "Viagem não encontrada")
    })
    public ResponseEntity<TripDto> confirmTrip(@PathVariable UUID id){
        return this.service.confirmTrip(id);
    }


    // ACTIVITY CRUD
    @PostMapping("/{id}/activities")
    @Operation(summary = "Cadastro de atividade", description = "Essa função é responsável por cadastrar uma atividade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ActivityCreateResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Viagem não encontrada")

    })
    public ResponseEntity<ActivityCreateResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        return this.service.registerActivity(id, payload);
    }

    @DeleteMapping("/{id}/activities/{activityId}")
    @Operation(summary = "Deleção de atividade", description = "Essa função é responsável por deletar uma atividade")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atividade deletada"),
            @ApiResponse(responseCode = "404", description = "Viagem não encontrada")

    })
    public ResponseEntity<Object> deleteActivity(@PathVariable UUID activityId) {
        return this.activityService.deleteActivity(activityId);
    }

    @GetMapping("/{id}/activities")
    @Operation(summary = "Listagem das atividades", description = "Esta função é responsável por listar todas as atividades de uma viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ActivityDto.class)))
            }),
    })
    public ResponseEntity<List<ActivityDto>> getAllActivitiesByTripId(@PathVariable UUID id) {
        List<ActivityDto> allParticipantsFromTrip = this.activityService.getAllActivitiesFromTrip(id);
        return ResponseEntity.ok(allParticipantsFromTrip);
    }

    @PutMapping("/{id}/activities/{activityId}")
    @Operation(summary = "Atualização de atividade", description = "Essa função é responsável por atualizar os dados de uma atividade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ActivityDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Atividade não encontrada")

    })
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable UUID activityId, @RequestBody ActivityRequestUpdatePayload payload) {
        return this.activityService.updateActivity(activityId, payload);
    }

    // LINKS CRUD
    @PostMapping("/{id}/links")
    @Operation(summary = "Cadastro de link", description = "Essa função é responsável por cadastrar um link")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LinkCreateResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Viagem não encontrada")

    })
    public ResponseEntity<LinkCreateResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        return this.service.registerLink(id, payload);
    }

    @PutMapping("/{id}/links/{linkId}")
    @Operation(summary = "Atualização de link", description = "Essa função é responsável por atualizar os dados de um link")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = LinkDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Link não encontrado")

    })
    public ResponseEntity<LinkDto> updateLink(@PathVariable UUID linkId, @RequestBody LinkRequestPayload payload) {
        return this.linkService.updateLink(linkId, payload);
    }


    @GetMapping("/{id}/links")
    @Operation(summary = "Listagem dos links", description = "Esta função é responsável por listar todos os links de uma viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = LinkDto.class)))
            }),
    })
    public ResponseEntity<List<LinkDto>> getAllLinksByTripId(@PathVariable UUID id) {
        List<LinkDto> allLinksFromTrip = this.linkService.getAllLinksFromTrip(id);
        return ResponseEntity.ok(allLinksFromTrip);
    }

    // ITEMS CRUD
    @PostMapping("/{id}/items")
    @Operation(summary = "Cadastro de item", description = "Essa função é responsável por cadastrar um item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ItemCreateResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Viagem não encontrada")

    })
    public ResponseEntity<ItemCreateResponse> registerItem(@PathVariable UUID id, @RequestBody ItemRequestPayload payload) {
        return this.service.registerItem(id, payload);
    }


    @GetMapping("/{id}/items")
    @Operation(summary = "Listagem dos itens", description = "Esta função é responsável por listar todos os itens de uma viagem")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemDto.class)))
            }),
             @ApiResponse(responseCode = "404", description = "Viagem não encontrada")
    })
    public ResponseEntity<List<ItemDto>> getAllItemsByTripId(@PathVariable UUID id) {
        List<ItemDto> allLinksFromTrip = this.itemService.getAllItemsFromTrip(id);
        return ResponseEntity.ok(allLinksFromTrip);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    @Operation(summary = "Deleção de item", description = "Essa função é responsável por deletar um item")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item deletado"),
            @ApiResponse(responseCode = "404", description = "Viagem não encontrada")

    })
    public ResponseEntity<Object> deleteItem(@PathVariable UUID itemId) {
        return this.itemService.deleteItem(itemId);
    }

}

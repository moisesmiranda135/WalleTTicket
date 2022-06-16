package com.salesianos.triana.dam.walleTTicket.controller;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.services.impl.TicketServiceImpl;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@SecurityRequirement(name = "walleTTicket")
@Tag(name = "Ticket",description = "Controlador para los Tickets")
public class TicketController {

    private final TicketServiceImpl ticketService;


    @Operation(summary = "Crea un nuevo Ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado 3l Ticket correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el Ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})
    })
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("fileTicket") MultipartFile fileTicket,
                                    @RequestPart("fileProduct") MultipartFile fileProduct,
                                    @Valid @RequestPart("json") CreateTicketDto dto,
                                    @AuthenticationPrincipal UserEntity u) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.save(dto, fileTicket, fileProduct, u));
    }

    @Operation(summary = "Obtiene una lista de todos los Tickets del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @GetMapping("/all")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @Operation(summary = "Obtiene una lista de todos los Tickets del agreagados a favoritos de un usuario loggeado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @GetMapping("/all/user/favorite")
    public ResponseEntity<?> listAll(@AuthenticationPrincipal UserEntity u) {
        return ResponseEntity.ok(ticketService.findAllUserByIsFavorite(u));
    }

    @Operation(summary = "Obtiene una lista de todos los Tickets de un usuario loggeado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @GetMapping("/user")
    public ResponseEntity<?> listUser(@AuthenticationPrincipal UserEntity u,
                                      @RequestParam(required = false) Optional<Long>idUser,
                                        @RequestParam(required = false) Optional<String> title,
                                      @RequestParam(required = false) Optional<String> categoryName,
                                      @RequestParam(required = false) Optional<String> companyName,
                                      @RequestParam(required = false) Optional<Double> minPrice,
                                      @RequestParam(required = false) Optional<Double> maxPrice) {

        return ResponseEntity.ok(ticketService.findAllUser(u, idUser, title, categoryName, companyName, minPrice, maxPrice));
    }

    @Operation(summary = "Optiene los detalles de un Ticket elegido por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el Ticket correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @GetMapping("/{id}")
    public GetTicketDto one(@PathVariable Long id) {
        return ticketService.findById(id);
    }


    @Operation(summary = "Edita los atributos de un Ticket existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el Ticket y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el Ticket indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CreateTicketDto> edit(@Valid @RequestPart("json") CreateTicketDto dto,
                                                @RequestPart("fileTicket") MultipartFile fileTicket,
                                                @RequestPart("fileProduct") MultipartFile fileProduct,
                                                @AuthenticationPrincipal UserEntity u,
                                                @PathVariable Long id) {

        return ResponseEntity.ok().body(ticketService.edit(dto, id, fileTicket, fileProduct, u));
    }

    @Operation(summary = "Borra un Ticket previamente creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el Ticket indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Agregar a favoritos a un Ticket previamente creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el Ticket indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @PostMapping("/favorite/add/{id}")
    public ResponseEntity<?> addFavorite(@PathVariable Long id) {
        ticketService.addFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Eliminar de favoritos a un Ticket previamente creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el Ticket indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @PostMapping("/favorite/delete/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        ticketService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}

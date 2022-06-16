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
public class TicketController {

    private final TicketServiceImpl ticketService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("fileTicket") MultipartFile fileTicket,
                                    @RequestPart("fileProduct") MultipartFile fileProduct,
                                    @Valid @RequestPart("json") CreateTicketDto dto,
                                    @AuthenticationPrincipal UserEntity u) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.save(dto, fileTicket, fileProduct, u));
    }

    @Operation(summary = "Obtiene una lista de todas los Tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ningún Tickets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))})})
    @GetMapping("/all")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/all/user/favorite")
    public ResponseEntity<?> listAll(@AuthenticationPrincipal UserEntity u) {
        return ResponseEntity.ok(ticketService.findAllUserByIsFavorite(u));
    }

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

    @GetMapping("/{id}")
    public GetTicketDto one(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateTicketDto> edit(@Valid @RequestPart("json") CreateTicketDto dto,
                                                @RequestPart("fileTicket") MultipartFile fileTicket,
                                                @RequestPart("fileProduct") MultipartFile fileProduct,
                                                @AuthenticationPrincipal UserEntity u,
                                                @PathVariable Long id) {

        return ResponseEntity.ok().body(ticketService.edit(dto, id, fileTicket, fileProduct, u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorite/add/{id}")
    public ResponseEntity<?> addFavorite(@PathVariable Long id) {
        ticketService.addFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorite/delete/{id}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        ticketService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}

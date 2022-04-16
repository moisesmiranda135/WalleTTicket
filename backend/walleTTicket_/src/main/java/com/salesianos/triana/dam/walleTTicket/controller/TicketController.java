package com.salesianos.triana.dam.walleTTicket.controller;



import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.services.impl.TicketServiceImpl;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketServiceImpl ticketService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("fileTicket") MultipartFile fileTicket,
                                    @RequestPart("fileProduct") MultipartFile fileProduct,
                                    @Valid @RequestPart("json") CreateTicketDto dto,
                                    @AuthenticationPrincipal UserEntity u) throws IOException {



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.save(dto, fileTicket,fileProduct, u));
    }

    @GetMapping("/")
    public ResponseEntity<?> list(@AuthenticationPrincipal UserEntity u) {
        return ResponseEntity.ok(ticketService.findAll(u));
    }
}

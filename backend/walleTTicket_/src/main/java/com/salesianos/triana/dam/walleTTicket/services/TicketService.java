package com.salesianos.triana.dam.walleTTicket.services;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TicketService {
    CreateTicketDto save(CreateTicketDto createTicketDto, MultipartFile fileTicket, MultipartFile fileProduct, UserEntity u) throws IOException;
    List<GetTicketDto> findAllUserLogged(UserEntity u);

}

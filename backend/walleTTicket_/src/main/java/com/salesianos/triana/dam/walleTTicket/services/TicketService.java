package com.salesianos.triana.dam.walleTTicket.services;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TicketService {
    CreateTicketDto save(CreateTicketDto createTicketDto, MultipartFile fileTicket, MultipartFile fileProduct, UserEntity u) throws IOException;
    List<GetTicketDto> findAll();
    List<GetTicketDto> findAllUser(UserEntity u,Optional<Long>idUser, Optional<String> title, Optional<String> categoryName, Optional<String> companyName,
                                   Optional<Double>minPrice, Optional<Double>maxPrice);
    List<GetTicketDto> findAllUserByIsFavorite(UserEntity u);
    GetTicketDto findById(Long id);
    CreateTicketDto edit(CreateTicketDto dto, Long id, MultipartFile fileTicket, MultipartFile fileProduct, UserEntity u);
    void deleteById (Long id);
    void addFavorite (Long id);
    void deleteFavorite (Long id);

}

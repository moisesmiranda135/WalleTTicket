package com.salesianos.triana.dam.walleTTicket.users.dto;

import com.salesianos.triana.dam.walleTTicket.dto.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.users.models.Roles;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String name;
    private String lastName;
    private String email;

    private String role;
}

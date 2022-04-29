package com.salesianos.triana.dam.walleTTicket.users.dto;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserToGetUserDto(UserEntity u) {
        return GetUserDto.builder()
                .name(u.getName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .avatarUrl(u.getAvatarUrl())
                .role(u.getRol().name())
                .isEnabled(u.isEnabled())
                .build();
    }

    public CreateUserDto convertUserToCreateUserDto(UserEntity u) {
        return CreateUserDto.builder()
                .name(u.getName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .password(u.getPassword())
                .avatarUrl(u.getAvatarUrl())
                .role(u.getRol().name())
                .isEnabled(u.isEnabled())
                .build();
    }
}

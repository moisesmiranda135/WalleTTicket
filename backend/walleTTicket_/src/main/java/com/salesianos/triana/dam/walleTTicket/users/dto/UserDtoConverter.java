package com.salesianos.triana.dam.walleTTicket.users.dto;

import com.salesianos.triana.dam.walleTTicket.users.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserToGetUserDto(User u) {
        return GetUserDto.builder()
                .name(u.getName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .role(u.getRol().name())
                .build();

    }
}

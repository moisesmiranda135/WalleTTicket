package com.salesianos.triana.dam.walleTTicket.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String name;
    private String lastName;
    private String email;
    private String avatarUrl;
    private boolean isEnabled;

    private String role;
}

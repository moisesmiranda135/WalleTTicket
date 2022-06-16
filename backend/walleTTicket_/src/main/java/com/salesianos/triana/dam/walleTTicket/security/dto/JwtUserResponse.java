package com.salesianos.triana.dam.walleTTicket.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String name;
    private String lastName;
    private String role;
    private String token;
    private Boolean isEnabled;

}
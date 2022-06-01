package com.salesianos.triana.dam.walleTTicket.users.dto;

import com.salesianos.triana.dam.walleTTicket.validators.simple.annotations.UniqueUser;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String name;
    private String lastName;
    @NotBlank(message = "{UserEntity.name.notblank}")
    @UniqueUser(message = "{UserEntity.name.uniqueName}")
    private String email;
    private String password;
    private String role;
    private boolean isEnabled;
}

package com.salesianos.triana.dam.walleTTicket.users.controller;

import com.salesianos.triana.dam.walleTTicket.users.dto.CreateUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.GetUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.UserDtoConverter;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import com.salesianos.triana.dam.walleTTicket.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoUser(@RequestPart("json") CreateUserDto newUser, @RequestPart("file") MultipartFile file) {
        UserEntity saved = userService.saveUser(newUser,file);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));

    }



    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> nuevoAdministrador(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userService.saveAdmin(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));

    }
}

package com.salesianos.triana.dam.walleTTicket.users.controller;

import com.salesianos.triana.dam.walleTTicket.users.dto.CreateUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.GetUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.UserDtoConverter;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import com.salesianos.triana.dam.walleTTicket.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> newUser(@RequestPart("json") CreateUserDto newUser) {
        UserEntity saved = userService.saveUser(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> newAdmin(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userService.saveAdmin(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @PostMapping("/auth/register/employee")
    public ResponseEntity<GetUserDto> newEmployee(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userService.saveEmployee(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @GetMapping("/auth/all")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/auth/all/employee")
    public ResponseEntity<?> listAllEmployee() {
        return ResponseEntity.ok(userService.findAllEmployee());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<CreateUserDto> editUser(@Valid @RequestPart("json") CreateUserDto dto,
                                                @AuthenticationPrincipal UserEntity u,
                                                @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolUser(dto, id, u));
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<CreateUserDto> editEmployee(@Valid @RequestPart("json") CreateUserDto dto,
                                                  @AuthenticationPrincipal UserEntity u,
                                                  @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolEmployee(dto, id, u));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CreateUserDto> editAdmin(@Valid @RequestPart("json") CreateUserDto dto,
                                                      @AuthenticationPrincipal UserEntity u,
                                                      @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolAdmin(dto, id, u));
    }


    @PostMapping("/user/enabled/{id}")
    public ResponseEntity<?> enabledUser(@PathVariable Long id, @AuthenticationPrincipal UserEntity u) {
        userService.enabledUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/disabled/{id}")
    public ResponseEntity<?> disabledUser(@PathVariable Long id, @AuthenticationPrincipal UserEntity u) {
        userService.disbledUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employee/enabled/{id}")
    public ResponseEntity<?> enabledEmployee(@PathVariable Long id) {
        userService.enabledUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employee/disabled/{id}")
    public ResponseEntity<?> disabledEmployee(@PathVariable Long id) {
        userService.disbledUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserEntity u) {
        userService.deleteUserById(id, u);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id, @AuthenticationPrincipal UserEntity u) {
        userService.deleteEmployeeById(id, u);
        return ResponseEntity.noContent().build();
    }
}

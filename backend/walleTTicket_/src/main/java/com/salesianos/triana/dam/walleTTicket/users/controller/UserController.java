package com.salesianos.triana.dam.walleTTicket.users.controller;

import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.users.dto.CreateUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.GetUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.UserDtoConverter;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import com.salesianos.triana.dam.walleTTicket.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "walleTTicket")
@Tag(name = "Usuario",description = "Controlador para los Usuarios")
public class UserController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;


    @Operation(summary = "Crea una nuevo Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el Usuario correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el Usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
    })
    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> newUser(@RequestPart("json") CreateUserDto newUser) {
        UserEntity saved = userService.saveUser(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @Operation(summary = "Crea una nuevo Administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el Administrador correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el Administrador",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permisos para crear el Administrador",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> newAdmin(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userService.saveAdmin(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @Operation(summary = "Crea una nuevo Empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el Empleado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el Empleado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permisos para crear el Empleado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})
    })
    @PostMapping("/auth/register/employee")
    public ResponseEntity<GetUserDto> newEmployee(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userService.saveEmployee(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserToGetUserDto(saved));
    }

    @Operation(summary = "Obtiene una lista de todas los Usuarios, Administradors y Empleados registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ninguno",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @GetMapping("/auth/all")
    public ResponseEntity<?> listAll() {

        List<UserEntity> data = userService.findAll();

            List<GetUserDto> result =
                    data.stream()
                            .map(userDtoConverter::convertUserToGetUserDto)
                            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtiene una lista de todas los Empleados registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Empleados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ningún Empleado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @GetMapping("/auth/all/employee")
    public ResponseEntity<?> listAllEmployee() {
        return ResponseEntity.ok(userService.findAllEmployee());
    }


    @Operation(summary = "Obtiene una lista de todas los Administradores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Administradores",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ningún Administrador",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @GetMapping("/auth/all/admin")
    public ResponseEntity<?> listAllAdmin() {
        return ResponseEntity.ok(userService.findAllAdmin());
    }


    @Operation(summary = "Edita los atributos de un Usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al Usuario y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado al Usuario indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @PutMapping("/user/{id}")
    public ResponseEntity<CreateUserDto> editUser(@Valid @RequestPart("json") CreateUserDto dto,
                                                @AuthenticationPrincipal UserEntity u,
                                                @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolUser(dto, id, u));
    }

    @Operation(summary = "Edita los atributos de un Empleado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al Empleado y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado al Empleado indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @PutMapping("/employee/{id}")
    public ResponseEntity<CreateUserDto> editEmployee(@Valid @RequestPart("json") CreateUserDto dto,
                                                  @AuthenticationPrincipal UserEntity u,
                                                  @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolEmployee(dto, id, u));
    }

    @Operation(summary = "Edita los atributos de un Administrador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al Administrador y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado al Administrador indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @PutMapping("/admin/{id}")
    public ResponseEntity<CreateUserDto> editAdmin(@Valid @RequestPart("json") CreateUserDto dto,
                                                      @AuthenticationPrincipal UserEntity u,
                                                      @PathVariable Long id) {

        return ResponseEntity.ok().body(userService.editRolAdmin(dto, id, u));
    }

    @Operation(summary = "Borra un Empleado previamente creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado al Empleado indicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))})})
    @DeleteMapping("employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id, @AuthenticationPrincipal UserEntity u) {
        userService.deleteEmployeeById(id, u);
        return ResponseEntity.noContent().build();
    }
}

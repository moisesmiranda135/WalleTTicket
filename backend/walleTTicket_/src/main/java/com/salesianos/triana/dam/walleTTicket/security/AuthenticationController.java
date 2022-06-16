package com.salesianos.triana.dam.walleTTicket.security;

import com.salesianos.triana.dam.walleTTicket.model.Company;
import com.salesianos.triana.dam.walleTTicket.security.dto.JwtUserResponse;
import com.salesianos.triana.dam.walleTTicket.security.dto.LoginDto;
import com.salesianos.triana.dam.walleTTicket.security.jwt.JwtProvider;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "walleTTicket")
@Tag(name = "Autenticación",description = "Controlador para la Autenticación")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Hacer Logging a la aplicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha realizado el Login a  correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido realizar el Login",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = jwtProvider.generateToken(authentication);


        UserEntity u = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(u, jwt));

    }

    @Operation(summary = "Obtiene los detalles del Usuario loggeado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al Usuario correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})})
    @GetMapping("/me")
    public ResponseEntity<?> aboutMe(@AuthenticationPrincipal UserEntity u){
        return ResponseEntity.ok(convertUserToJwtUserResponse(u, null));
    }


    private JwtUserResponse convertUserToJwtUserResponse(UserEntity u, String jwt) {
        return JwtUserResponse.builder()
                .name(u.getName())
                .lastName(u.getLastName())
                .email(u.getEmail())
                .role(u.getRol().name())
                .isEnabled(u.isEnabled())
                .token(jwt)
                .build();
    }


}

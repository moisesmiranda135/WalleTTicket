package com.salesianos.triana.dam.walleTTicket.security;

import com.salesianos.triana.dam.walleTTicket.security.dto.JwtUserResponse;
import com.salesianos.triana.dam.walleTTicket.security.dto.LoginDto;
import com.salesianos.triana.dam.walleTTicket.security.jwt.JwtProvider;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
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
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

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

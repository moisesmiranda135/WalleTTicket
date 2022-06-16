package com.salesianos.triana.dam.walleTTicket;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "walleTTicket", scheme = "Bearer", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "WalleTTicket API", version = "BETA", description = "Informacón de la API de WalleTTicket"))
public class WalleTTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalleTTicketApplication.class, args);
	}

}

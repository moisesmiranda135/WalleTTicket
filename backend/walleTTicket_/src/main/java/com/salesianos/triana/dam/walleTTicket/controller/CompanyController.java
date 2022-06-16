package com.salesianos.triana.dam.walleTTicket.controller;

import com.salesianos.triana.dam.walleTTicket.dto.Company.CreateCompanyDto;
import com.salesianos.triana.dam.walleTTicket.dto.Company.GetCompanyDto;
import com.salesianos.triana.dam.walleTTicket.model.Company;
import com.salesianos.triana.dam.walleTTicket.services.impl.CompanyServiceImpl;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@SecurityRequirement(name = "walleTTicket")
@Tag(name = "Compañía",description = "Controlador para las Compañías")
public class CompanyController {

    private final CompanyServiceImpl companyService;

    @Operation(summary = "Crea una nueva Compañía")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la Compañía correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la Compañía",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})
    })
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestPart("json") CreateCompanyDto dto, @RequestPart("file") MultipartFile file) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.save(dto, file));
    }

    @Operation(summary = "Obtiene una lista de todas las Compañías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Compañías",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado ninguna Compañía",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})})
    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(companyService.findAll());
    }


    @Operation(summary = "Optiene los detalles de la Compañía elegida por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Compañía correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})})
    @GetMapping("/{id}")
    public GetCompanyDto one(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @Operation(summary = "Edita los atributos de una Compañía existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Compañía y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la Compañía indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CreateCompanyDto> edit(@Valid @RequestPart("json") CreateCompanyDto dto,
                                                 @RequestPart("file") MultipartFile file,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok().body(companyService.edit(dto,file, id));
    }

    @Operation(summary = "Borra una Compañía previamente creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la Compañía indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

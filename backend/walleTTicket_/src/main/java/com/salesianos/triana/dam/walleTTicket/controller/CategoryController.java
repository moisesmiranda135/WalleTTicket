package com.salesianos.triana.dam.walleTTicket.controller;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.model.Category;
import com.salesianos.triana.dam.walleTTicket.model.Company;
import com.salesianos.triana.dam.walleTTicket.services.impl.CategoryServiceImpl;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@SecurityRequirement(name = "walleTTicket")
@Tag(name = "Categoría",description = "Controlador para las Categorías")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Operation(summary = "Crea una nueva Categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la Categoría correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la Categoría",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Company.class))})
    })
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateCategoryDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(dto));
    }

    @Operation(summary = "Obtiene una lista de todas las Categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la lista de Categorías",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna Categoría",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Optiene los detalles de la Categoría elegida por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Categoría correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @GetMapping("/{id}")
    public GetCategoryDto one(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "Edita los atributos de una Categoría existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Categoría y se ha modificado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la Categoría indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<CreateCategoryDto> edit(@RequestBody CreateCategoryDto dto, @PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.edit(dto, id));
    }

    @Operation(summary = "Borra una Categoría previamente creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))}),


            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la Categoría indicada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Category.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

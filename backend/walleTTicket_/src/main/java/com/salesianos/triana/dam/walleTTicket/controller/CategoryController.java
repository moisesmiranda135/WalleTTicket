package com.salesianos.triana.dam.walleTTicket.controller;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestPart("json") CreateCategoryDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(dto));
    }

    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public GetCategoryDto one(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateCategoryDto> edit(@Valid @RequestPart("json") CreateCategoryDto dto, @PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.edit(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

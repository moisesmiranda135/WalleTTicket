package com.salesianos.triana.dam.walleTTicket.controller;

import com.salesianos.triana.dam.walleTTicket.dto.Company.CreateCompanyDto;
import com.salesianos.triana.dam.walleTTicket.dto.Company.GetCompanyDto;
import com.salesianos.triana.dam.walleTTicket.services.impl.CompanyServiceImpl;
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
public class CompanyController {

    private final CompanyServiceImpl companyService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestPart("json") CreateCompanyDto dto, @RequestPart("file") MultipartFile file) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.save(dto, file));
    }

    @GetMapping("/")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public GetCompanyDto one(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateCompanyDto> edit(@Valid @RequestPart("json") CreateCompanyDto dto,
                                                 @RequestPart("file") MultipartFile file,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok().body(companyService.edit(dto,file, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

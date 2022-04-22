package com.salesianos.triana.dam.walleTTicket.dto.Company;

import com.salesianos.triana.dam.walleTTicket.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter {

    public GetCompanyDto convertCompanyToGetCompanyDto(Company c) {
        return GetCompanyDto.builder()
                .id(c.getId())
                .name(c.getName())
                .image(c.getImage())
                .build();

    }

    public CreateCompanyDto convertCompanyToCreateCompanyDto(Company c) {
        return CreateCompanyDto.builder()
                .id(c.getId())
                .name(c.getName())
                .image(c.getImage())
                .build();

    }

    public CreateCompanyDto convertCompanyToEditCompanyDto(Company c) {
        return CreateCompanyDto.builder()
                .id(c.getId())
                .name(c.getName())
                .image(c.getImage())
                .build();

    }
}

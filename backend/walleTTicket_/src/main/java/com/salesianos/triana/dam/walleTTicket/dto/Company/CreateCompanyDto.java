package com.salesianos.triana.dam.walleTTicket.dto.Company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyDto {

    private Long id;

    private String name;
    private String image;
}

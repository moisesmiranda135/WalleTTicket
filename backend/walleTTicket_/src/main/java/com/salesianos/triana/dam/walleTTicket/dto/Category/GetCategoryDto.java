package com.salesianos.triana.dam.walleTTicket.dto.Category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCategoryDto {

    private Long id;

    private String title;
    private String icon;
}

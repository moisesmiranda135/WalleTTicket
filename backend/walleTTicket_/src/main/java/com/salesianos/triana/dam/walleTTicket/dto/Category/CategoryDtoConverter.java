package com.salesianos.triana.dam.walleTTicket.dto.Category;

import com.salesianos.triana.dam.walleTTicket.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter {

    public GetCategoryDto convertCategoryToGetCategoryDto(Category c) {
        return GetCategoryDto.builder()
                .id(c.getId())
                .title(c.getTitle())
                .icon(c.getIcon())
                .build();

    }

    public CreateCategoryDto convertCategoryToCreateCategoryDto(Category c) {
        return CreateCategoryDto.builder()
                .id(c.getId())
                .title(c.getTitle())
                .icon(c.getIcon())
                .build();
    }

}

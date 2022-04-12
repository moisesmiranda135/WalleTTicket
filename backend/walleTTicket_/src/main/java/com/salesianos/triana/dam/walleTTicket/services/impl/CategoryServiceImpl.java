package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CategoryDtoConverter;
import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.model.Category;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.repos.CategoryRepository;
import com.salesianos.triana.dam.walleTTicket.services.CategoryService;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryDtoConverter converter;

    @Override
    public CreateCategoryDto save(CreateCategoryDto createCategoryDto){

        Category c = repository.save(Category.builder()
                .id(createCategoryDto.getId())
                .title(createCategoryDto.getTitle())
                .icon(createCategoryDto.getIcon())
                .build());

        return converter.convertCategoryToCreateCategoryDto(Category.builder()
                .id(c.getId())
                .title(c.getTitle())
                .icon(c.getIcon())
                .build());
    }

    @Override
    public List<GetCategoryDto> findAll() {
        List<Category> data = repository.findAll();

        if (data.isEmpty()) {
            throw new ListEntityNotFoundException(Category.class);
        } else {
            List<GetCategoryDto> result =
                    data.stream()
                            .map(converter::convertCategoryToGetCategoryDto)
                            .collect(Collectors.toList());
            return result;
        }
    }
}

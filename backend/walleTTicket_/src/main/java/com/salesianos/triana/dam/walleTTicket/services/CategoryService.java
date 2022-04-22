package com.salesianos.triana.dam.walleTTicket.services;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CreateCategoryDto save(CreateCategoryDto createCategoryDto);
    List<GetCategoryDto> findAll();
    GetCategoryDto findById(Long id);
    CreateCategoryDto edit(CreateCategoryDto category, Long id);
    void deleteById (Long id);
}

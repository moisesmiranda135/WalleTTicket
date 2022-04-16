package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CategoryDtoConverter;
import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.model.Category;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.repos.CategoryRepository;
import com.salesianos.triana.dam.walleTTicket.repos.TicketRepository;
import com.salesianos.triana.dam.walleTTicket.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryDtoConverter converter;
    private final TicketRepository ticketRepository;

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

    @Override
    public GetCategoryDto findById(Long id) {

        Optional<Category> data = repository.findById(id);

        if (data.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Category.class);
        } else {
            List<GetCategoryDto> result =
                    data.stream()
                            .map(converter::convertCategoryToGetCategoryDto)
                            .collect(Collectors.toList());

            return converter.convertCategoryToGetCategoryDto(repository.findById(id).get());
        }

    }


    @Override
    public CreateCategoryDto edit (CreateCategoryDto category, Long id) {
        return repository.findById(id).map(c -> {
            c.setId(id);
            c.setIcon(category.getIcon());
            c.setTitle(category.getTitle());
            repository.save(c);
            return converter.editCategory(c);
        }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), Category.class));

    }

    @Override
    public void deleteById(Long id) {

        Optional<Category> category = repository.findById(id);
        if(category.isEmpty()){
            throw new SingleEntityNotFoundException(id.toString(),Category.class);
        }else{

            List<Ticket> ticketList = ticketRepository.findAllTicketByCategoryId(id);
            ticketList.forEach(t -> {
                t.setCategory(null);
                ticketRepository.save(t);
            });
            repository.deleteById(id);
        }
    }


}

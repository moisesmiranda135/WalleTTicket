package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.TicketDtoConverter;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.model.Category;
import com.salesianos.triana.dam.walleTTicket.model.Company;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.repos.CategoryRepository;
import com.salesianos.triana.dam.walleTTicket.repos.CompanyRepository;
import com.salesianos.triana.dam.walleTTicket.repos.TicketRepository;
import com.salesianos.triana.dam.walleTTicket.services.TicketService;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import com.salesianos.triana.dam.walleTTicket.users.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repository;
    private final TicketDtoConverter converter;
    private final S3Service s3Service;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public CreateTicketDto save(CreateTicketDto createTicketDto, MultipartFile fileTicket, MultipartFile fileProduct, UserEntity u) throws IOException {
        String ticketUrl=s3Service.save(fileTicket);
        String productUrl=s3Service.save(fileProduct);

        Optional<Category> categoryExist = categoryRepository.findById(createTicketDto.getCategoryId());
        Optional<Company> companyExist = companyRepository.findById(createTicketDto.getCompanyId());

        if (categoryExist.isEmpty()){
            throw new SingleEntityNotFoundException(createTicketDto.getCategoryId().toString(), Category.class);
        }
        if (companyExist.isEmpty()){
            throw new SingleEntityNotFoundException(createTicketDto.getCompanyId().toString(), Company.class);
        }

        Ticket t = repository.save(Ticket.builder()
                .id(createTicketDto.getId())
                .title(createTicketDto.getTitle())
                .description(createTicketDto.getDescription())
                .price(createTicketDto.getPrice())
                .category(categoryExist.get())
                .company(companyExist.get())
                .dateInit(createTicketDto.getDateInit())
                .dateEnd(createTicketDto.getDateEnd())
                .ticketImage(createTicketDto.getTicketImage())
                .productImage(createTicketDto.getProductImage())
                .isFavorite(createTicketDto.getIsFavorite())
                .userEntity(u)
                .build());

        return converter.convertTicketToCreateTicketDto(Ticket.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .price(t.getPrice())
                .category(categoryExist.get())
                .company(companyExist.get())
                .dateInit(t.getDateInit())
                .dateEnd(t.getDateEnd())
                .ticketImage(t.getTicketImage())
                .productImage(t.getProductImage())
                .isFavorite(t.getIsFavorite())
                .userEntity(t.getUserEntity())
                .build());
    }

    @Override
    public List<GetTicketDto> findAll(UserEntity u) {

        List<Ticket> data = repository.findAll();

        if (data.isEmpty()) {
            throw new ListEntityNotFoundException(Ticket.class);
        } else {
            List<GetTicketDto> result =
                    data.stream()
                            .map(converter::convertTicketToGetTicketDto)
                            .collect(Collectors.toList());
            return result;
        }


    }
}

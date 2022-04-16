package com.salesianos.triana.dam.walleTTicket.services;

import com.salesianos.triana.dam.walleTTicket.dto.Category.CreateCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Category.GetCategoryDto;
import com.salesianos.triana.dam.walleTTicket.dto.Company.CreateCompanyDto;
import com.salesianos.triana.dam.walleTTicket.dto.Company.GetCompanyDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    CreateCompanyDto save(CreateCompanyDto createCompanyDto, MultipartFile file) throws IOException;
    List<GetCompanyDto> findAll();
    GetCompanyDto findById(Long id);
    CreateCompanyDto edit(CreateCompanyDto dto, MultipartFile file, Long id);
    void deleteById (Long id);

}

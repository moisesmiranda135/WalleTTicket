package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.salesianos.triana.dam.walleTTicket.dto.Company.CompanyDtoConverter;
import com.salesianos.triana.dam.walleTTicket.dto.Company.CreateCompanyDto;
import com.salesianos.triana.dam.walleTTicket.dto.Company.GetCompanyDto;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.model.Company;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.repos.CompanyRepository;
import com.salesianos.triana.dam.walleTTicket.repos.TicketRepository;
import com.salesianos.triana.dam.walleTTicket.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyDtoConverter converter;
    private final S3Service s3Service;

    private final TicketRepository ticketRepository;


    @Override
    public CreateCompanyDto save(CreateCompanyDto createCompanyDto, MultipartFile file) throws IOException {
        String imageUrl=s3Service.save(file);


        Company c = repository.save(Company.builder()
                .id(createCompanyDto.getId())
                .name(createCompanyDto.getName())
                .image(imageUrl)
                .build());

        return converter.convertCompanyToCreateCompanyDto(Company.builder()
                .id(c.getId())
                .name(c.getName())
                .image(c.getImage())
                .build());
    }

    @Override
    public List<GetCompanyDto> findAll() {
        List<Company> data = repository.findAll();

        if (data.isEmpty()) {
            throw new ListEntityNotFoundException(Company.class);
        } else {
            List<GetCompanyDto> result =
                    data.stream()
                            .map(converter::convertCompanyToGetCompanyDto)
                            .collect(Collectors.toList());
            return result;
        }
    }

    @Override
    public GetCompanyDto findById(Long id) {
        Optional<Company> data = repository.findById(id);

        if (data.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Company.class);
        } else {
            List<GetCompanyDto> result =
                    data.stream()
                            .map(converter::convertCompanyToGetCompanyDto)
                            .collect(Collectors.toList());

            return converter.convertCompanyToGetCompanyDto(repository.findById(id).get());
        }
    }

    @Override
    public CreateCompanyDto edit(CreateCompanyDto dto, MultipartFile file, Long id) {
        Optional<Company> company = repository.findById(id);

        s3Service.deleteImage(company.get().getImage());

        String imageUrl=s3Service.save(file);


        return repository.findById(id).map(c -> {
            c.setId(id);
            c.setName(dto.getName());
            c.setImage(imageUrl);
            repository.save(c);
            return converter.convertCompanyToEditCompanyDto(c);
        }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), Company.class));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Company> company = repository.findById(id);
        s3Service.deleteImage(company.get().getImage());

        if(company.isEmpty()){
            throw new SingleEntityNotFoundException(id.toString(),Company.class);
        }else{

            List<Ticket> ticketList = ticketRepository.findAllTicketByCompanyId(id);
            ticketList.forEach(t -> {
                t.setCompany(null);
                ticketRepository.save(t);
            });
            repository.deleteById(id);
        }
    }
}

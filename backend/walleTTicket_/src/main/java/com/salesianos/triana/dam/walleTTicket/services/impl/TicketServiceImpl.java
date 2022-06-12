package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.salesianos.triana.dam.walleTTicket.dto.Ticket.CreateTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.GetTicketDto;
import com.salesianos.triana.dam.walleTTicket.dto.Ticket.TicketDtoConverter;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.ListEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.NotAuthorizationException;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        String ticketUrl = s3Service.save(fileTicket);
        String productUrl = s3Service.save(fileProduct);

        Optional<Category> categoryExist = categoryRepository.findById(createTicketDto.getCategoryId());
        Optional<Company> companyExist = companyRepository.findById(createTicketDto.getCompanyId());

        if (categoryExist.isEmpty()) {
            throw new SingleEntityNotFoundException(createTicketDto.getCategoryId().toString(), Category.class);
        }
        if (companyExist.isEmpty()) {
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
                .ticketImage(ticketUrl)
                .productImage(productUrl)
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
    public List<GetTicketDto> findAll() {

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

    @Override
    public List<GetTicketDto> findAllUser(UserEntity u, Optional<Long> idUser, Optional<String> title, Optional<String> categoryName, Optional<String> companyName,
                                          Optional<Double>minPrice, Optional<Double>maxPrice) {

        Specification<Ticket> specTitle = new Specification<Ticket>() {
            @Override
            public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (title.isPresent()){
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.get().toLowerCase() + "%");
                }
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Ticket> specUser = new Specification<Ticket>() {
            @Override
            public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (idUser.isPresent()){
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("userEntityId")), "%" + idUser.get() + "%");
                }
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        /*
        static Specification<Book> hasAuthor(String author) {
            return (book, cq, cb) -> cb.equal(book.get("author"), author);
        }

         */

        /*
        Specification<Ticket> specTitle = new Specification<Ticket>() {
            @Override
            public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (title.isPresent()){
                    return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.get().toLowerCase() + "%");
                }
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };
         */

        Specification<Ticket> sTitle = (root, query, criteriaBuilder) -> {
            if (title.isPresent()){
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.get().toLowerCase() + "%");
                //return criteriaBuilder.equal(root.get("title"), title.get());
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };

        Specification<Ticket> sUser = (root, query, criteriaBuilder) -> {
            if (u != null){
                //return criteriaBuilder.like(criteriaBuilder.lower(root.get("userEntityId")), "%" + idUser.get() + "%");
                return criteriaBuilder.equal(root.get("userEntity"), u);
            }
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };

        //Specification<Ticket> all = specTitle.and(specUser);
        Specification<Ticket> all = sUser.and(sTitle);

        List<Ticket> data = repository.findAll(all);

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

    @Override
    public List<GetTicketDto> findAllUserByIsFavorite(UserEntity u) {
        List<Ticket> data = repository.findAllTicketByIsFavoriteAndUserEntity(true, u);

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

    @Override
    public GetTicketDto findById(Long id) {
        Optional<Ticket> data = repository.findById(id);

        if (data.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Ticket.class);
        } else {
            List<GetTicketDto> result =
                    data.stream()
                            .map(converter::convertTicketToGetTicketDto)
                            .collect(Collectors.toList());

            return converter.convertTicketToGetTicketDto(repository.findById(id).get());
        }
    }

    @Override
    public CreateTicketDto edit(CreateTicketDto dto, Long id, MultipartFile fileTicket, MultipartFile fileProduct, UserEntity u) {
        Optional<Ticket> ticket = repository.findById(id);

        if (ticket.get().getUserEntity() != u) {
            throw new NotAuthorizationException();
        }
        s3Service.deleteImage(ticket.get().getProductImage());
        s3Service.deleteImage(ticket.get().getTicketImage());

        String ticketUrl = s3Service.save(fileTicket);
        String productUrl = s3Service.save(fileProduct);

        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        Optional<Company> company = companyRepository.findById(dto.getCompanyId());

        return repository.findById(id).map(t -> {
            t.setId(id);
            t.setTitle(dto.getTitle());
            t.setDescription(dto.getDescription());
            t.setPrice(dto.getPrice());
            t.setDateInit(dto.getDateInit());
            t.setDateEnd(dto.getDateEnd());
            t.setProductImage(productUrl);
            t.setTicketImage(ticketUrl);
            t.setIsFavorite(dto.getIsFavorite());
            t.setCompany(company.get());
            t.setCategory(category.get());
            t.setUserEntity(u);
            repository.save(t);
            return converter.convertTicketToCreateTicketDto(t);
        }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), Company.class));

    }

    @Override
    public void deleteById(Long id) {
        Optional<Ticket> ticket = repository.findById(id);
        if (ticket.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Ticket.class);
        } else {
            ticket.get().setCategory(null);
            ticket.get().setCompany(null);
            s3Service.deleteImage(ticket.get().getTicketImage());
            s3Service.deleteImage(ticket.get().getProductImage());

            repository.deleteById(id);
        }
    }

    @Override
    public void addFavorite(Long id) {
        Optional<Ticket> ticket = repository.findById(id);
        if (ticket.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Ticket.class);
        } else {
            ticket.get().setIsFavorite(true);
            repository.save(ticket.get());
        }
    }

    @Override
    public void deleteFavorite(Long id) {
        Optional<Ticket> ticket = repository.findById(id);
        if (ticket.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), Ticket.class);
        } else {
            ticket.get().setIsFavorite(false);
            repository.save(ticket.get());
        }
    }


}

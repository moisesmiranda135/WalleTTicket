package com.salesianos.triana.dam.walleTTicket.repos;

import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    List<Ticket> findAllTicketByCategoryId(Long id);
    List<Ticket> findAllTicketByCompanyId(Long id);
    List<Ticket> findByUserEntityOrderByDateInitDesc(UserEntity userEntity);

    List<Ticket> findAllTicketByIsFavoriteAndUserEntity(Boolean isFavorite, UserEntity userEntity);

    //List<Ticket> findAllTicketByUserEntityId(Long id);
    // List<Ticket> findByUserEntityId(Long id);  // Esta es correcta
}

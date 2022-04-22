package com.salesianos.triana.dam.walleTTicket.repos;

import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllTicketByCategoryId(Long id);
    List<Ticket> findAllTicketByCompanyId(Long id);
}

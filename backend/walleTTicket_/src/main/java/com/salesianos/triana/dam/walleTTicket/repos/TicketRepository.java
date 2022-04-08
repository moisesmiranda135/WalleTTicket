package com.salesianos.triana.dam.walleTTicket.repos;

import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

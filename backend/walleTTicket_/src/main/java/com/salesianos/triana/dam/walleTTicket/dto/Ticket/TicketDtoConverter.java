package com.salesianos.triana.dam.walleTTicket.dto.Ticket;

import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoConverter {

    public GetTicketDto convertTicketToGetTicketDto(Ticket t) {
        return GetTicketDto.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .price(t.getPrice())
                .categoryName(t.getCategory().getTitle())
                .companyName(t.getCategory().getTitle())
                .dateInit(t.getDateInit())
                .dateEnd(t.getDateEnd())
                .ticketImage(t.getTicketImage())
                .productImage(t.getProductImage())
                .isFavorite(t.getIsFavorite())
                .build();

    }

    public CreateTicketDto convertTicketToCreateTicketDto(Ticket t) {
        return CreateTicketDto.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .price(t.getPrice())
                .categoryId(t.getCategory().getId())
                .companyId(t.getCategory().getId())
                .dateInit(t.getDateInit())
                .dateEnd(t.getDateEnd())
                .ticketImage(t.getTicketImage())
                .productImage(t.getProductImage())
                .isFavorite(t.getIsFavorite())
                .build();

    }
}

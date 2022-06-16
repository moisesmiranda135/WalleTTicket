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
                .categoryIcon(t.getCategory().getIcon())
                .companyName(t.getCompany().getName())
                .companyImage(t.getCompany().getImage())
                .dateInit(t.getDateInit())
                .dateEnd(t.getDateEnd())
                .ticketImage(t.getTicketImage())
                .productImage(t.getProductImage())
                .isFavorite(t.getIsFavorite())
                .userName(t.getUserEntity().getName())
                .userLastName(t.getUserEntity().getLastName())
                .userEmail(t.getUserEntity().getEmail())
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

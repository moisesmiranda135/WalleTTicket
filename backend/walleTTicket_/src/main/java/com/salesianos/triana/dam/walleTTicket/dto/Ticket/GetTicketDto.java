package com.salesianos.triana.dam.walleTTicket.dto.Ticket;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTicketDto {

    private String title;
    private String description;
    private String price;
    private LocalDate dateInit;
    private LocalDate  dateEnd;
    private String ticketImage;
    private String productImage;
    private Boolean isFavorite;

    private String categoryName;
    private String companyName;
}

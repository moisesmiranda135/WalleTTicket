package com.salesianos.triana.dam.walleTTicket.dto.Ticket;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTicketDto {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private LocalDate dateInit;
    private LocalDate  dateEnd;
    private String ticketImage;
    private String productImage;
    private Boolean isFavorite;

    private String categoryName;
    private String companyName;
}

package com.salesianos.triana.dam.walleTTicket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTicketDto {

    private String title;
    private String description;
    private String price;
    private String dateInit;
    private String dateEnd;
    private String image;

    private String userName;

    // string categoria
    // string compañia
}

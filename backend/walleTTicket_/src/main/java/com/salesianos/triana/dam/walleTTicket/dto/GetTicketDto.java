package com.salesianos.triana.dam.walleTTicket.dto;

import com.salesianos.triana.dam.walleTTicket.users.models.User;
import lombok.*;

import javax.persistence.ManyToOne;

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

package com.salesianos.triana.dam.walleTTicket.model;


import com.salesianos.triana.dam.walleTTicket.users.models.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String price;
    private String dateInit;
    private String dateEnd;
    private String image;

    @ManyToOne
    private User user;

    // ocjeto categoria
    // objeto compañia


}

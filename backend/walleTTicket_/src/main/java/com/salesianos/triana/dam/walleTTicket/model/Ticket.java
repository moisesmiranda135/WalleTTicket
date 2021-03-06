package com.salesianos.triana.dam.walleTTicket.model;


import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Double price;
    private LocalDate dateInit;
    private LocalDate  dateEnd;
    private String ticketImage;
    private String productImage;
    private Boolean isFavorite;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Company company;
}

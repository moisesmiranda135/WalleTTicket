package com.salesianos.triana.dam.walleTTicket.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    //Texto necesario para pintar el icono, el usuario seleccionará una imagen

    private String icon;
    private String image;

    @OneToMany(mappedBy = "category")
    private List<Ticket> ticketsList = new ArrayList<>();


}

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
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;

    @OneToMany(mappedBy = "company")
    private List<Ticket> ticketsList = new ArrayList<>();
}

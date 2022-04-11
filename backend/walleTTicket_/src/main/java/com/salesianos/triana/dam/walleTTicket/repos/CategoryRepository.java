package com.salesianos.triana.dam.walleTTicket.repos;

import com.salesianos.triana.dam.walleTTicket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

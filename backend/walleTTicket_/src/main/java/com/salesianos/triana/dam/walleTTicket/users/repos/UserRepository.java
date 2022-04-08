package com.salesianos.triana.dam.walleTTicket.users.repos;

import com.salesianos.triana.dam.walleTTicket.users.models.Roles;
import com.salesianos.triana.dam.walleTTicket.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    List<User> findByRol (Roles rol);
}

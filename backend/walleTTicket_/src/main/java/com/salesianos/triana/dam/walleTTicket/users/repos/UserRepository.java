package com.salesianos.triana.dam.walleTTicket.users.repos;

import com.salesianos.triana.dam.walleTTicket.users.models.Roles;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String email);

    List<UserEntity> findByRol (Roles rol);

    boolean existsByEmail(String email);
}

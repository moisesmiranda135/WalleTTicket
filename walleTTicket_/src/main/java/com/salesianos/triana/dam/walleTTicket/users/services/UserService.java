package com.salesianos.triana.dam.walleTTicket.users.services;

import com.salesianos.triana.dam.walleTTicket.services.base.BaseService;
import com.salesianos.triana.dam.walleTTicket.users.dto.CreateUserDto;
import com.salesianos.triana.dam.walleTTicket.users.models.Roles;
import com.salesianos.triana.dam.walleTTicket.users.models.User;
import com.salesianos.triana.dam.walleTTicket.users.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }


    public List<User> loadUserByRole(Roles roles) throws UsernameNotFoundException{
        return this.repositorio.findByRol(roles);
    }

    public Optional<User> loadUserById(Long id) throws UsernameNotFoundException{
        return this.repositorio.findById(id);
    }



    public User saveUser(CreateUserDto newUser){

            User user = User.builder()
                    .name(newUser.getName())
                    .lastName(newUser.getLastName())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .rol(Roles.USER)
                    .build();

            return save(user);
    }

    public User saveAdmin(CreateUserDto newUser) {

            User user = User.builder()
                    .name(newUser.getName())
                    .lastName(newUser.getLastName())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .rol(Roles.ADMIN)
                    .build();
            return save(user);
    }

    @PostConstruct
    public List<User>findAll() {
        repositorio.findAll().stream().forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            repositorio.save(u);
        });
        return repositorio.findAll();
    }

}

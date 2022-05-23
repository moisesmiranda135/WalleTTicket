package com.salesianos.triana.dam.walleTTicket.users.services;

import com.salesianos.triana.dam.walleTTicket.errors.exceptions.NotAuthorizationException;
import com.salesianos.triana.dam.walleTTicket.errors.exceptions.SingleEntityNotFoundException;
import com.salesianos.triana.dam.walleTTicket.model.Ticket;
import com.salesianos.triana.dam.walleTTicket.services.base.BaseService;
import com.salesianos.triana.dam.walleTTicket.services.impl.S3Service;
import com.salesianos.triana.dam.walleTTicket.users.dto.CreateUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.GetUserDto;
import com.salesianos.triana.dam.walleTTicket.users.dto.UserDtoConverter;
import com.salesianos.triana.dam.walleTTicket.users.models.Roles;
import com.salesianos.triana.dam.walleTTicket.users.models.UserEntity;
import com.salesianos.triana.dam.walleTTicket.users.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserService extends BaseService<UserEntity, Long, UserRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter converter;
    private final S3Service s3Service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }


    public List<UserEntity> loadUserByRole(Roles roles) throws UsernameNotFoundException{
        return this.repositorio.findByRol(roles);
    }

    public Optional<UserEntity> loadUserById(Long id) throws UsernameNotFoundException{
        return this.repositorio.findById(id);
    }


    public UserEntity saveUser(CreateUserDto newUser, MultipartFile file){
        String avatarUrl=s3Service.save(file);

            UserEntity userEntity = UserEntity.builder()
                    .name(newUser.getName())
                    .lastName(newUser.getLastName())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .avatarUrl(avatarUrl)
                    .rol(Roles.USER)
                    .isEnabled(true)
                    .build();

            return save(userEntity);
    }

    public UserEntity saveEmployee(CreateUserDto newUser){

        UserEntity userEntity = UserEntity.builder()
                .name(newUser.getName())
                .lastName(newUser.getLastName())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .email(newUser.getEmail())
                .rol(Roles.EMPLOYEE)
                .isEnabled(true)
                .build();

        return save(userEntity);
    }

    public UserEntity saveAdmin(CreateUserDto newUser) {

            UserEntity userEntity = UserEntity.builder()
                    .name(newUser.getName())
                    .lastName(newUser.getLastName())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .rol(Roles.ADMIN)
                    .build();
            return save(userEntity);
    }

    @PostConstruct
    public List<UserEntity>findAll() {
        repositorio.findAll().stream().forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            repositorio.save(u);
        });
        return repositorio.findAll();
    }

    @PostConstruct
    public List<UserEntity>findAllEmployee() {
        repositorio.findAll().stream().forEach(u -> {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            repositorio.save(u);
        });
        return repositorio.findByRol(Roles.EMPLOYEE);
    }

    public GetUserDto findUserById(Long id) {
        Optional<UserEntity> data = repositorio.findById(id);

        if (data.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else {
            List<GetUserDto> result =
                    data.stream()
                            .map(converter::convertUserToGetUserDto)
                            .collect(Collectors.toList());

            return converter.convertUserToGetUserDto(repositorio.findById(id).get());
        }
    }

    public CreateUserDto editRolUser(CreateUserDto dto, MultipartFile file, Long id, UserEntity us) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else if (us.getRol() == Roles.EMPLOYEE || us.getRol() == Roles.ADMIN || us.getId() == id) {
            s3Service.deleteImage(user.get().getAvatarUrl());
            String avatarUrl=s3Service.save(file);

            return repositorio.findById(id).map(u -> {
                u.setId(id);
                u.setName(dto.getName());
                u.setLastName(dto.getLastName());
                u.setPassword(dto.getPassword());
                u.setEmail(dto.getEmail());
                u.setAvatarUrl(avatarUrl);
                u.setEnabled(true);
                repositorio.save(u);
                return converter.convertUserToCreateUserDto(u);
            }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), UserEntity.class));
        } else {
            throw new NotAuthorizationException();
        }
    }

    public CreateUserDto editRolEmployee(CreateUserDto dto, MultipartFile file, Long id, UserEntity us) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else if (us.getRol() == Roles.ADMIN || us.getId() == id) {
            s3Service.deleteImage(user.get().getAvatarUrl());
            String avatarUrl=s3Service.save(file);

            return repositorio.findById(id).map(u -> {
                u.setId(id);
                u.setName(dto.getName());
                u.setLastName(dto.getLastName());
                u.setPassword(dto.getPassword());
                u.setEmail(dto.getEmail());
                u.setAvatarUrl(avatarUrl);
                u.setEnabled(true);
                repositorio.save(u);
                return converter.convertUserToCreateUserDto(u);
            }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), UserEntity.class));
        } else {
            throw new NotAuthorizationException();
        }
    }

    public CreateUserDto editRolAdmin(CreateUserDto dto, MultipartFile file, Long id, UserEntity us) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else if (us.getRol() == Roles.ADMIN || us.getId() == id) {
            s3Service.deleteImage(user.get().getAvatarUrl());
            String avatarUrl=s3Service.save(file);

            return repositorio.findById(id).map(u -> {
                u.setId(id);
                u.setName(dto.getName());
                u.setLastName(dto.getLastName());
                u.setPassword(dto.getPassword());
                u.setEmail(dto.getEmail());
                u.setAvatarUrl(avatarUrl);
                u.setEnabled(true);
                repositorio.save(u);
                return converter.convertUserToCreateUserDto(u);
            }).orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), UserEntity.class));
        } else {
            throw new NotAuthorizationException();
        }
    }

    public void enabledUser(Long id) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        }
            user.get().setEnabled(true);
            repositorio.save(user.get());
    }

    public void disbledUser(Long id) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        }
        user.get().setEnabled(false);
        repositorio.save(user.get());
    }

    public void deleteUserById(Long id, UserEntity u) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else if (u.getRol() == Roles.ADMIN || u.getRol() == Roles.EMPLOYEE || u.getId() == id) {
            s3Service.deleteImage(user.get().getAvatarUrl());
            user.get().setTicketsList(null);

            repositorio.deleteById(id);
        } else {
            throw new NotAuthorizationException();
        }
    }

    public void deleteEmployeeById(Long id, UserEntity u) {
        Optional<UserEntity> user = repositorio.findById(id);
        if (user.isEmpty()) {
            throw new SingleEntityNotFoundException(id.toString(), UserEntity.class);
        } else if (u.getRol() == Roles.ADMIN || u.getId() == id) {
            user.get().setTicketsList(null);

            repositorio.deleteById(id);
        } else {
            throw new NotAuthorizationException();
        }
    }
}

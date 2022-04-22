package com.salesianos.triana.dam.walleTTicket.validators.simple.validations;

import com.salesianos.triana.dam.walleTTicket.users.repos.UserRepository;
import com.salesianos.triana.dam.walleTTicket.validators.simple.annotations.UniqueUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UniqueUser constraintAnnotation) { }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return StringUtils.hasText(email) && !repository.existsByEmail(email);
    }
}

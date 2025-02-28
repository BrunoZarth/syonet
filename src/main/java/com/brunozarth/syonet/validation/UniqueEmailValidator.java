package com.brunozarth.syonet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.brunozarth.syonet.repository.ClientRepository;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private ClientRepository clientRepository;

    @Autowired
    public UniqueEmailValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public UniqueEmailValidator() {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        return clientRepository != null && !clientRepository.findByEmail(email).isPresent();
    }
}

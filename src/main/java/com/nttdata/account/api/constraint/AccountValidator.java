package com.nttdata.account.api.constraint;

import com.nttdata.account.api.request.AccountRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class AccountValidator implements ConstraintValidator<ValidAccount, AccountRequest> {

    @Override
    public void initialize(ValidAccount constraintAnnotation) {
    }

    @Override
    public boolean isValid(AccountRequest customerRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(customerRequest.getAccountHolders()) || customerRequest.getAccountHolders().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "El campo 'acountHolders' debe tener al menos un titular")
                .addConstraintViolation();
            return false;
        }
        return true;
    }


}

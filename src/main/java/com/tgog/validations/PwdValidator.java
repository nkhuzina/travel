package com.tgog.validations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PwdValidator implements
        ConstraintValidator<com.tgog.annotation.PasswordValidator, String> {

    List<String> weakPasswords;

    @Override
    public void initialize(com.tgog.annotation.PasswordValidator passwordValidator) {
        weakPasswords = Arrays.asList("12345", "password", "qwerty");
    }

    @Override
    public boolean isValid(String passwordField,
                           ConstraintValidatorContext cxt) {
        return passwordField != null && (!weakPasswords.contains(passwordField));
    }
}

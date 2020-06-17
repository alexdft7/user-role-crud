package ru.codemark.userroleservice.helper;

import ru.codemark.userroleservice.entity.User;

import java.util.function.IntPredicate;

public class PasswordValidityCheck {

    StringBuilder errorMessage = new StringBuilder();

    public boolean passed(String value) {
        return containsLowerCase(value) &&
                containsUpperCase(value) &&
                containsNumber(value);
    }

    private boolean containsLowerCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
    }

    private boolean containsUpperCase(String value) {
        return contains(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
    }

    private boolean containsNumber(String value) {
        return contains(value, Character::isDigit);
    }

    private boolean contains(String value, IntPredicate predicate) {
        return value.chars().anyMatch(predicate);
    }

    public String getErrorMessage(User user) {

        if (!containsLowerCase(user.getPassword())) {
            errorMessage.append("doesn't contain any lowercase letters, ");
        }
        if (!containsUpperCase(user.getPassword())) {
            errorMessage.append("doesn't contain any uppercase letters, ");
        }
        if (!containsNumber(user.getPassword())) {
            errorMessage.append("doesn't contain any numbers, ");
        }
        if (user.getName().length() == 0) {
            errorMessage.append("name is empty, ");
        }
        if (user.getLogin().length() == 0) {
            errorMessage.append("login is empty, ");
        }

        errorMessage.deleteCharAt(errorMessage.length()-1);
        errorMessage.deleteCharAt(errorMessage.length()-1);

        return errorMessage.toString();
    }
}
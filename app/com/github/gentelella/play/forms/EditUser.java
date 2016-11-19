package com.github.gentelella.play.forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.i18n.Messages;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fre on 19/11/16.
 */
public class EditUser {

    @Constraints.Required
    @Constraints.MinLength(2)
    public String name;
    @Constraints.Required
    @Constraints.MinLength(2)
    public String surname;
    @Constraints.Required
    @Constraints.MinLength(4)
    public String password;
    @Constraints.Required
    @Constraints.MinLength(4)
    public String confirmPassword;

    //TODO use a client side translation instead

    public List<ValidationError> validate() {
        List<ValidationError> errors = new LinkedList<ValidationError>();
        if (!password.equals(confirmPassword)) {
            errors.add(new ValidationError("confirmPassword", Messages.get("error.passwordDoesNotMatch")));
        }
        return errors.isEmpty() ? null : errors;
    }
}
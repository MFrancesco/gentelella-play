package com.github.gentelella.play.controllers;

import com.github.gentelella.play.models.User;
import com.github.gentelella.play.security.CryptoUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.pac4j.play.java.Secure;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.mvc.Result;
import views.html.admin.createAdmin;
import views.html.admin.createUser;
import views.html.sample.index;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by fre on 09/07/16.
 */
@Singleton
public class AdminController extends BaseLoggedController {

    private final FormFactory formFactory;
    private final CryptoUtils cryptoUtils;
    private final Form<CreateUser> createUserForm;
    private final Form<CreateAdmin> createAdminForm;
    private final Gson gson;

    @Inject
    public AdminController(FormFactory formFactory, CryptoUtils cryptoUtils){
        this.formFactory = formFactory;
        createUserForm = formFactory.form(CreateUser.class);
        createAdminForm = formFactory.form(CreateAdmin.class);
        gson = new GsonBuilder().create();
        this.cryptoUtils = cryptoUtils;
    }

    public static class CreateUser {
        @Constraints.Required
        public String name;
        @Constraints.Required
        public String surname;
        @Constraints.Required
        @Constraints.MinLength(4)
        public String password;
        @Constraints.Required
        @Constraints.MinLength(4)
        public String confirmPassword;
        @Constraints.Required
        @Constraints.Email
        public String email;

        public List<ValidationError> validate() {
            List<ValidationError> errors = new LinkedList<ValidationError>();
            if (!password.equals(confirmPassword)) {
                errors.add(new ValidationError("confirmPassword", "La password non corrisponde", new LinkedList<Object>()));
            }
            User user = User.find.where().eq("email", email).findUnique();
            if (user != null)
                errors.add(new ValidationError("email", "Utente con questa email già esistente"));
            return errors.isEmpty() ? null : errors;
        }
    }

    public static class CreateAdmin {
        @Constraints.Required
        public String name;
        @Constraints.Required
        public String surname;
        @Constraints.Required
        @Constraints.MinLength(4)
        public String password;
        @Constraints.Required
        @Constraints.MinLength(4)
        public String confirmPassword;
        @Constraints.Required
        @Constraints.Email
        public String email;


        public List<ValidationError> validate() {
            List<ValidationError> errors = new LinkedList<ValidationError>();
            if (!password.equals(confirmPassword)) {
                errors.add(new ValidationError("confirmPassword", "La password non corrisponde", new LinkedList<Object>()));
            }
            User user = User.find.where().eq("email", email).findUnique();
            if (user != null)
                errors.add(new ValidationError("email", "Utente con questa email già esistente"));
            return errors.isEmpty() ? null : errors;
        }
    }


    @Secure(clients = "FormClient", authorizers = "admin")
    public Result onlyAdmin() {
        Logger.info("Only admins can reach this");
        return ok(index.render());
    }


    @Secure(clients = "FormClient", authorizers = "admin")
    public Result createUser() {
        return ok(createUser.render());
    }

    @Secure(clients = "FormClient", authorizers = "admin")
    public Result createAdmin() {

        return ok(createAdmin.render());
    }

    @Transactional
    @Secure(clients = "FormClient", authorizers = "admin")
    public Result createUserPost() {
        Form<CreateUser> form = createUserForm.bindFromRequest();
        if (form.hasErrors()){
            return badRequest(form.errorsAsJson());
        }
        CreateUser formResult = form.get();
        //Remember, encrypting pwd here
        User user = new User(formResult.name, formResult.surname, formResult.email, cryptoUtils.encrypt(formResult.password), User.UserType.USER);
        user.save();
        return ok("");
    }

    @Transactional
    @Secure(clients = "FormClient", authorizers = "admin")
    public Result createAdminPost() {
        Form<CreateAdmin> form = createAdminForm.bindFromRequest();
        if (form.hasErrors()){
            return badRequest(form.errorsAsJson());
        }
        CreateAdmin formResult = form.get();
        //Remember, encrypting pwd here
        User user = new User(formResult.name, formResult.surname, formResult.email, cryptoUtils.encrypt(formResult.password), User.UserType.ADMIN);
        user.save();
        return ok("");
    }

}






















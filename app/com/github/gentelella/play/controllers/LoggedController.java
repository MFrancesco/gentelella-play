package com.github.gentelella.play.controllers;

import com.github.gentelella.play.forms.EditUser;
import com.github.gentelella.play.models.User;
import com.github.gentelella.play.security.CryptoUtils;
import com.google.inject.Inject;
import org.pac4j.core.config.Config;
import org.pac4j.play.java.Secure;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.mvc.Result;
import views.html.admin.indexAdmin;
import views.html.sample.samplecontent;
import views.html.sample.simpleBase;
import views.html.user.editUser;
import views.html.user.indexUser;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by fre on 09/07/16.
 * Controller where all methods has to be accessibile only to logged user, thos users can be of type user or admin
 */
@Singleton
public class LoggedController extends BaseLoggedController {

    private final CryptoUtils cryptoUtils;
    private final Config config;
    private final FormFactory formFactory;
    private final Form<EditUser> editUserForm;

    @Inject
    public LoggedController(Config config, FormFactory formFactory, CryptoUtils cryptoUtils){
        this.config = config;
        this.formFactory = formFactory;
        this.cryptoUtils = cryptoUtils;
        editUserForm = formFactory.form(EditUser.class);
    }




    @Secure(clients = "FormClient" )
    public Result index() {
        if (isUser())
            return ok(indexUser.render(getUser()));
        else{
            List<User> users = User.find.where().eq("type", User.UserType.USER).findList();
            List<User> admins = User.find.where().eq("type", User.UserType.USER).findList();
            return ok(indexAdmin.render(admins,users));
        }
    }

    @Secure(clients = "FormClient" )
    public Result base() {return ok(simpleBase.render());}

    @Secure(clients = "FormClient")
    public Result baseExtended() {return ok(samplecontent.render());}

    @Secure(clients = "FormClient")
    public Result editProfile() {
        return ok(editUser.render(getUser()));
    }

    @Transactional
    @Secure(clients = "FormClient")
    public Result editProfilePost() {
        Form<EditUser> formResult = editUserForm.bindFromRequest();
        if (formResult.hasErrors()){
            return badRequest(formResult.errorsAsJson());
        }
        User user = getUser();
        user.name = formResult.get().name;
        user.surname = formResult.get().surname;
        user.password = cryptoUtils.encrypt(formResult.get().password);
        user.update();
        return ok("");
    }
}
















































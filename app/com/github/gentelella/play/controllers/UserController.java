package com.github.gentelella.play.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.pac4j.play.java.Secure;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import views.html.user.fileForm;
import java.io.File;

/**
 * Created by fre on 10/12/16.
 */
public class UserController extends BaseLoggedController {

    private final static long MAX_FILE_LENGHT= (long) Math.pow(2d,20d);
    private final Form<UserController.FileForm> fileFormForm;


    @Inject
    public UserController(FormFactory formFactory){
        this.fileFormForm = formFactory.form(UserController.FileForm.class);
    }

    public static class FileForm{
        @Constraints.Required
        public String field1;
    }

    @Secure(clients = "FormClient", authorizers = "user")
    public Result fileForm(){
        return ok(fileForm.render());
    }

    @Secure(clients = "FormClient", authorizers = "user")
    public Result fileFormPost(){
        Form<FileForm> formResult = fileFormForm.bindFromRequest();
        if (fileFormForm.hasErrors()){
            return badRequest(formResult.errorsAsJson());
        }
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        File file = body.getFile("file").getFile();
        System.out.println(String.format("Received file of size %d bytes with full path %s ", file.length() , file.getAbsolutePath()) );
        if (file == null || file.length() > MAX_FILE_LENGHT){
            formResult.reject("file", Message("file.invalid"));
            return badRequest(formResult.errorsAsJson());
        }
        return ok(Message("file.valid"));
    }
}

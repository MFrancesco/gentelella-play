package com.github.gentelella.play.controllers;

import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import play.i18n.MessagesApi;
import play.mvc.*;
import views.html.sample.*;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This controller contains actions to handle HTTP requests
 * to the application's home page and to all that routes that are not secured
 */
@Singleton
public class HomeController extends TranslateController {

    @Inject
    private Config config;

    public Result login(String username, String error) {
        String url = ((FormClient) config.getClients().findClient("FormClient")).getCallbackUrl();
        if (error!=null)
            error= Message("error.CredentialException");
        return ok (login.render(url, username, error));
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(index.render());
    }

    public Result base() {return ok(simpleBase.render());}

    public Result baseExtended() {return ok(samplecontent.render());}

    public Result serverError() {return ok (error500.render("This message is setted in the controller")); }

    public Result notFoundError() {return ok (error404.render()); }
}

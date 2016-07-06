package com.github.gentelella.play.controllers;

import play.mvc.*;

import views.html.*;
import views.html.sample.index;
import views.html.sample.login;
import views.html.sample.samplecontent;
import views.html.sample.error404;
import views.html.sample.error500;
import views.html.sample.simpleBase;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

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

    public Result login() {return ok (login.render()); }

    public Result serverError() {return ok (error500.render("This message is setted in the controller")); }

    public Result notFoundError() {return ok (error404.render()); }
}

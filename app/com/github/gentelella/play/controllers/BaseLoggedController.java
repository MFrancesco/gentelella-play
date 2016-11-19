package com.github.gentelella.play.controllers;

import com.github.gentelella.play.models.User;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import static play.mvc.Controller.ctx;

/**
 * This class contains some utility methods that will be
 * used by the controllers whose methods require the users to be logged in
 */
public abstract class BaseLoggedController extends TranslateController {


    //Role related utility methods
    private static CommonProfile getProfile(){
        PlayWebContext context = new PlayWebContext(ctx());
        ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
        //Fetch the profile from the current session, return a new empty profile if none is present
        return profileManager.get(true).orElse(new CommonProfile());
    }

    /**
     * @return the current logged user in the method call, NULL otherwise
     */
    public static User getUser(){
        return User.find.where().eq("email", getProfile().getEmail()).findUnique();
    }

    public static boolean isAdmin(){
        return getProfile().getRoles().contains(User.UserType.ADMIN.toString());
    }

    public static boolean isUser(){ return getProfile().getRoles().contains(User.UserType.USER.toString()); }

}

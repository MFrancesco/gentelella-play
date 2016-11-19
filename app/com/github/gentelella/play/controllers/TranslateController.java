package com.github.gentelella.play.controllers;

import play.i18n.MessagesApi;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * Created by fre on 15/11/16.
 * A simple controller that give us the translaction capability using the MessagesApi
 * All the controller are going to extend this
 */
public abstract class TranslateController extends Controller {
    @Inject
    private MessagesApi messages;

    public String Message(String key){
        return messages.preferred(request()).at(key);
    }

    public String Message(String key, Object... args){
        return messages.preferred(request()).at(key,args);
    }
}

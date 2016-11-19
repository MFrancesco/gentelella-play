package com.github.gentelella.play.controllers;

import play.Configuration;
import play.Environment;
import play.Logger;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.i18n.MessagesApi;
import play.mvc.Http.RequestHeader;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.mvc.Results;
import views.html.sample.error404;
import views.html.utils.error;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    private final MessagesApi messagesApi;

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment, OptionalSourceMapper sourceMapper, Provider<Router> routes, MessagesApi messagesApi) {
        super(configuration, environment, sourceMapper, routes);
        this.messagesApi = messagesApi;
    }

    @Override
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        if (statusCode == Status.NOT_FOUND){
            return CompletableFuture.completedFuture(Results.notFound(error404.render()));
        }
        return CompletableFuture.completedFuture(
                Results.status(statusCode, error.render(statusCode, messagesApi.preferred(request).at("ClientSideError") , message))
        );
    }

    @Override
    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        Logger.error(exception.getMessage(), exception);
        return CompletableFuture.completedFuture(
                Results.internalServerError(error.render(500, messagesApi.preferred(request).at("InternalServerError") ,exception.getMessage()))
        );
    }

}
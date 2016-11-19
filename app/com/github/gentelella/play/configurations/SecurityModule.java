package com.github.gentelella.play.configurations;


import com.github.gentelella.play.models.User;
import com.github.gentelella.play.security.CryptoUtils;
import com.github.gentelella.play.security.DbBasedUsernamePasswordAuthenticator;
import com.google.inject.AbstractModule;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.ApplicationLogoutController;
import org.pac4j.play.CallbackController;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import org.pac4j.play.store.PlayCacheStore;
import play.Configuration;
import play.Environment;

public class SecurityModule extends AbstractModule {


    private final Configuration configuration;
    private final CryptoUtils cryptoUtils;

    public SecurityModule(
            Environment environment,
            Configuration configuration) {
        this.configuration = configuration;
        this.cryptoUtils = new CryptoUtils(configuration);
    }

    @Override
    protected void configure() {
        final String baseUrl = configuration.getString("baseUrl");

        // HTTP
        final FormClient formClient = new FormClient(baseUrl+"/login", new DbBasedUsernamePasswordAuthenticator(cryptoUtils));

        final Clients clients = new Clients(formClient);
        clients.setCallbackUrl(baseUrl+"/callback");

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer(User.UserType.ADMIN.toString()));
        config.addAuthorizer("user",  new RequireAnyRoleAuthorizer(User.UserType.USER.toString()));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        bind(Config.class).toInstance(config);

        // set profile timeout to 2h instead of the 1h default
        PlayCacheStore store = new PlayCacheStore();
        store.setProfileTimeout(7200);
        config.setSessionStore(store);

        // callback
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setMultiProfile(true);
        bind(CallbackController.class).toInstance(callbackController);
        // logout
        final ApplicationLogoutController logoutController = new ApplicationLogoutController();
        logoutController.setDefaultUrl("/login");
        bind(ApplicationLogoutController.class).toInstance(logoutController);
    }
}
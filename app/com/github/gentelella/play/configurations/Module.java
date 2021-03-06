package com.github.gentelella.play.configurations;

import com.github.gentelella.play.security.CryptoUtils;
import com.github.gentelella.play.services.ApplicationScheduler;
import com.github.gentelella.play.services.ApplicationTimer;
import com.github.gentelella.play.services.Counter;
import com.google.inject.AbstractModule;
import java.time.Clock;

import com.github.gentelella.play.services.AtomicCounter;
import play.Configuration;

import javax.inject.Inject;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();

        //Bind application scheduler
        bind(ApplicationScheduler.class).asEagerSingleton();

        // Set AtomicCounter as the implementation for Counter.
        bind(Counter.class).to(AtomicCounter.class);

        // Bind CryptoUtils, used to encrypt the user passwords using the application secret as salt
        bind(CryptoUtils.class).asEagerSingleton();
    }

}

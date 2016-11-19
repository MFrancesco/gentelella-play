package com.github.gentelella.play.services;

import akka.actor.ActorSystem;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by fre on 02/07/16
 * a simple ApplicationScheduler that will shedule a print job every minute
 */
@Singleton
public class ApplicationScheduler {

    @Inject
    public ApplicationScheduler(ActorSystem actorSystem, ExecutionContextExecutor exec) {
        actorSystem.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.MINUTES), () ->  {
            System.out.println("Job executed every minute");
        }, exec);
    }
}

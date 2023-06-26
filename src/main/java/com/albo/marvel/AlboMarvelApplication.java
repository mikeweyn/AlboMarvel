package com.albo.marvel;

import com.albo.marvel.aplication.port.in.SuperheroSearchQuery;
import com.albo.marvel.domain.SuperHeroApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@SpringBootApplication
public class AlboMarvelApplication {

    @Autowired
    private SuperheroSearchQuery superheroSearchQuery;

    public static void main(String[] args) {
        SpringApplication.run(AlboMarvelApplication.class, args);
    }

    @Scheduled(cron = "${someJob.cron}")
    void someJob() throws InterruptedException {

        List<SuperHeroApp> superHeroApps = superheroSearchQuery.getSuperHeroAll();

        if (superHeroApps.size() > 0) {
            for (SuperHeroApp superHero : superHeroApps) {
                superheroSearchQuery.superheroSearch(superHero.getName());
                Thread.sleep(1000L);
            }
            log.info("Actualizando {} Super Hero" , superHeroApps.size() );
        } else {
            log.info("Nada que Actualizar");
        }

    }


}

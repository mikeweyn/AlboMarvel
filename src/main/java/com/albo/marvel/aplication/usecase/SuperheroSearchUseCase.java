package com.albo.marvel.aplication.usecase;

import com.albo.marvel.aplication.port.in.SuperheroSearchQuery;
import com.albo.marvel.aplication.port.on.ColaboratorsRepository;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.domain.SuperHeroApp;
import com.albo.marvel.domain.Superhero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SuperheroSearchUseCase implements SuperheroSearchQuery {

    SuperheroSearchRepository superheroSearchRepository;
    ColaboratorsRepository colaboratorsRepository;

    private SuperheroSearchUseCase( SuperheroSearchRepository superheroSearchRepository, ColaboratorsRepository colaboratorsRepository){
        this.superheroSearchRepository = superheroSearchRepository;
        this.colaboratorsRepository = colaboratorsRepository;

    }

    @Override
    public Superhero superheroSearch(String name) {

        Superhero superhero = superheroSearchRepository.superheroRepository(name);
        log.info("Obtengo la Información del superHeroe Marvel {}" , superhero);

        superheroSearchRepository.saveOrUpdateSuperHero(superhero);
        log.info("Actualizo ultima sincronización del SuperHero");

        return superhero;
    }

    @Override
    public List<SuperHeroApp> getSuperHeroAll() {
        return superheroSearchRepository.superHeroAllRepository();

    }
}

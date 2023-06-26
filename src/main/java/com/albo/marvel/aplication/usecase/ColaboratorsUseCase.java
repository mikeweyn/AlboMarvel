package com.albo.marvel.aplication.usecase;

import com.albo.marvel.adapter.impl.model.ColaboratorsAppJdbcModel;
import com.albo.marvel.aplication.port.in.ColaboratorsQuery;
import com.albo.marvel.aplication.port.on.ColaboratorsRepository;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.domain.Colaborators;
import com.albo.marvel.domain.Superhero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ColaboratorsUseCase implements ColaboratorsQuery {

    SuperheroSearchRepository superheroSearchRepository;
    ColaboratorsRepository colaboratorsRepository;

    private ColaboratorsUseCase( SuperheroSearchRepository superheroSearchRepository, ColaboratorsRepository colaboratorsRepository){
        this.superheroSearchRepository = superheroSearchRepository;
        this.colaboratorsRepository = colaboratorsRepository;

    }


    @Override
    public ColaboratorsAppJdbcModel execute(String name) {
        Superhero superhero = superheroSearchRepository.superheroRepository(name);
        log.info("Obtengo la Información del superHeroe Marvel {}" , superhero);

        superheroSearchRepository.saveOrUpdateSuperHero(superhero);
        log.info("Actualizo ultima sincronización del SuperHero");

        Colaborators colaborators = colaboratorsRepository.getColaborators(superhero);
        log.info("Obtengo la Información de los colaborators Marvel {}" , colaborators);

        ColaboratorsAppJdbcModel colaboratorsAppJdbcModel = colaboratorsRepository.getColaboratorsApp(superhero);
        log.info("Formateamos la Salida de los datos colaborators Marvel {}" , colaboratorsAppJdbcModel);

        return colaboratorsAppJdbcModel;
    }
}

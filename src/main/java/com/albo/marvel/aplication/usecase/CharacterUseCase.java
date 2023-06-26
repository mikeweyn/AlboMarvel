package com.albo.marvel.aplication.usecase;

import com.albo.marvel.adapter.impl.model.CharactersAppJdbcModel;
import com.albo.marvel.aplication.port.in.CharacterQuery;
import com.albo.marvel.aplication.port.on.CharacterRepository;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.domain.Character;
import com.albo.marvel.domain.Superhero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CharacterUseCase implements CharacterQuery {

    SuperheroSearchRepository superheroSearchRepository;

    CharacterRepository characterRepository;

    CharacterUseCase(SuperheroSearchRepository superheroSearchRepository, CharacterRepository characterRepository) {
        this.superheroSearchRepository = superheroSearchRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public List<CharactersAppJdbcModel> execute(String name) {

        Superhero superhero = superheroSearchRepository.superheroRepository(name);
        log.info("Obtengo la Información del superHeroe Marvel {}", superhero);

        superheroSearchRepository.saveOrUpdateSuperHero(superhero);
        log.info("Actualizo ultima sincronización del SuperHero");

        Character character = characterRepository.getCharacter(superhero);
        log.info("Obtengo la Información de los characters Marvel {}" , character);

        List<CharactersAppJdbcModel> characterAppJdbcModel =  characterRepository.charactersApp(superhero);

        return characterAppJdbcModel;


    }
}

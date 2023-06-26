package com.albo.marvel.aplication.port.on;

import com.albo.marvel.adapter.impl.model.SuperHeroAppJdbcModel;
import com.albo.marvel.domain.SuperHeroApp;
import com.albo.marvel.domain.Superhero;

import java.util.List;

public interface SuperheroSearchRepository {

    Superhero superheroRepository (String name);

    void saveOrUpdateSuperHero(Superhero superhero);

    SuperHeroAppJdbcModel superheroIdRepository(Superhero superhero);

    List<SuperHeroApp> superHeroAllRepository();
}

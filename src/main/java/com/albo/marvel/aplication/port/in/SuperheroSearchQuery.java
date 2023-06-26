package com.albo.marvel.aplication.port.in;

import com.albo.marvel.domain.SuperHeroApp;
import com.albo.marvel.domain.Superhero;

import java.util.List;

public interface SuperheroSearchQuery {

    Superhero superheroSearch (String name);

    List<SuperHeroApp> getSuperHeroAll();
}

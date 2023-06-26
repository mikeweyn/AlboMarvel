package com.albo.marvel.adapter.controller;

import com.albo.marvel.adapter.controller.model.SuperheroRest;
import com.albo.marvel.aplication.port.in.SuperheroSearchQuery;
import com.albo.marvel.domain.Superhero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("marvel")
public class ComicsAdapter {

    private SuperheroSearchQuery superheroSearchQuery;

    public ComicsAdapter(SuperheroSearchQuery superheroSearchQuery) {
        this.superheroSearchQuery = superheroSearchQuery;
    }

    @GetMapping(value = "/superhero/{characters}")
    public SuperheroRest getSuperHero(@PathVariable("characters") String characters) {
        log.info("Superheroe para comic de Superheroe {}", characters);

        Superhero superhero = superheroSearchQuery.superheroSearch(characters);
        SuperheroRest superheroRest = SuperheroRest.toSuperheroRest(superhero);


        return superheroRest;
    }
}

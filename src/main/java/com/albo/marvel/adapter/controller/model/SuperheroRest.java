package com.albo.marvel.adapter.controller.model;

import com.albo.marvel.domain.Superhero;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SuperheroRest {

    long id;
    String name;
    String description;

    public static SuperheroRest toSuperheroRest(Superhero superhero){
        return SuperheroRest.builder()
                .id(superhero.getData().getResults().get(0).getId())
                .name(superhero.getData().getResults().get(0).getName())
                .description(superhero.getData().getResults().get(0).getDescription())
                .build();
    }
}

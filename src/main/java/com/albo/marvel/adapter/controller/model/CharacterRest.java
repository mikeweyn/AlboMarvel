package com.albo.marvel.adapter.controller.model;

import com.albo.marvel.adapter.impl.model.CharactersAppJdbcModel;
import com.albo.marvel.adapter.impl.model.ComicsAppJdbcModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;
import java.util.List;

@Value
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CharacterRest {

    @JsonIgnore
    Timestamp last_sync;
    String character;
    List<ComicsAppJdbcModel> comics;

    public static CharacterRest toCharacterRest(CharactersAppJdbcModel charactersApp){
        return CharacterRest.builder()
                .last_sync(charactersApp.getLast_sync())
                .character(charactersApp.getCharacter())
                .comics(charactersApp.getComics())
                .build();
    }
}

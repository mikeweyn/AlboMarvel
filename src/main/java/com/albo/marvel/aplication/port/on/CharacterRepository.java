package com.albo.marvel.aplication.port.on;

import com.albo.marvel.adapter.impl.model.CharactersAppJdbcModel;
import com.albo.marvel.domain.Character;
import com.albo.marvel.domain.Superhero;

import java.util.List;

public interface CharacterRepository {

    Character getCharacter(Superhero superhero);

    List<CharactersAppJdbcModel> charactersApp (Superhero superhero);
}

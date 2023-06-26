package com.albo.marvel.adapter.controller;

import com.albo.marvel.adapter.controller.model.CharacterRest;
import com.albo.marvel.adapter.impl.model.CharactersAppJdbcModel;
import com.albo.marvel.aplication.port.in.CharacterQuery;
import com.albo.marvel.domain.Character;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("marvel")
public class CharactersAdapter {

    CharacterQuery characterQuery;

    CharactersAdapter(CharacterQuery characterQuery){
        this.characterQuery = characterQuery;
    }


    @GetMapping(value = "/characters/{characters}")
    public List<CharacterRest> getColaborators(@PathVariable("characters") String characters) {

        List<CharactersAppJdbcModel> character = characterQuery.execute(characters);

        List<CharacterRest> characterRest = character.stream().map(CharacterRest::toCharacterRest).collect(Collectors.toList());


        return characterRest;
    }
}

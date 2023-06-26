package com.albo.marvel.aplication.port.in;

import com.albo.marvel.adapter.impl.model.CharactersAppJdbcModel;

import java.util.List;

public interface CharacterQuery {

    List<CharactersAppJdbcModel> execute(String name);
}

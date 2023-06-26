package com.albo.marvel.adapter.impl;

import com.albo.marvel.adapter.impl.exception.ExeptionRest;
import com.albo.marvel.adapter.impl.model.*;
import com.albo.marvel.aplication.port.on.CharacterRepository;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.config.SqlReader;
import com.albo.marvel.domain.Character;
import com.albo.marvel.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class CharacterImplAdapter implements CharacterRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private static final String PATH_CREATE_CHARACTER = "sql/character.sql";
    private static final String PATH_CHARACTER_FOR_ID = "sql/characterforid.sql";
    private static final String PATH_COMIC_FOR_ID = "sql/charractercomic.sql";
    private String createcCharacterQuery;
    private String searchCharacterForIdQuery;

    private String searhchCharacteComicQuery;

    private RestTemplate marvelClientRest;

    PathVariableRest pathVariableRest;

    SuperheroSearchRepository superheroSearchRepository;

    private MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    CharacterImplAdapter(NamedParameterJdbcTemplate jdbcTemplate, PathVariableRest pathVariableRest, RestTemplate marvelClientRest, SuperheroSearchRepository superheroSearchRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.createcCharacterQuery = SqlReader.readSql(PATH_CREATE_CHARACTER);
        this.searchCharacterForIdQuery = SqlReader.readSql(PATH_CHARACTER_FOR_ID);
        this.searhchCharacteComicQuery = SqlReader.readSql(PATH_COMIC_FOR_ID);
        this.pathVariableRest = pathVariableRest;
        this.marvelClientRest = marvelClientRest;
        this.superheroSearchRepository = superheroSearchRepository;
    }

    @Override
    public Character getCharacter(Superhero superhero) {
        Character character = null;
        try {
            character = marvelClientRest.getForObject("https://gateway.marvel.com:443/v1/public/comics?format=comic&formatType=comic&noVariants=true&characters={characters}&ts={ts}&apikey={apikey}&hash={hash}", Character.class, pathVariableRest.getPathVariable(superhero));
            saveCharacter(character, superhero);
        } catch (Exception ex) {
            log.error("Error al Ejecutar Request ", ex.getMessage());
            throw new ExeptionRest(ex);
        }

        return character;
    }


    private void saveCharacter(Character character, Superhero superhero) {

        List<ResultsCharacter> resultsCharacter = character.getData().getResults();
        List<Characters> characters = resultsCharacter.stream().map(r -> new Characters(r.getCharacters().getItems())).collect(Collectors.toList());


        log.info("items {}", characters);

        for (ResultsCharacter chart : resultsCharacter) {
            sqlParameterSource.addValue("id", superhero.getData().getResults().get(0).getId());
            sqlParameterSource.addValue("id_comic", chart.getId());
            sqlParameterSource.addValue("comics", chart.getTitle());

            for (ItemsChacters items : chart.getCharacters().getItems()) {
                sqlParameterSource.addValue("character", items.getName());
                jdbcTemplate.update(createcCharacterQuery, sqlParameterSource);
            }

        }

    }

    @Override
    public List<CharactersAppJdbcModel> charactersApp(Superhero superhero) {

        SuperHeroAppJdbcModel sh = superheroSearchRepository.superheroIdRepository(superhero);
        List<CharactersAppJdbcModel> charactersAppJdbcModelList = new LinkedList<>();

        List<CharacterJdbcModel> characterJdbcModels = jdbcTemplate.query(searchCharacterForIdQuery,
                pathVariableRest.getPathVariableSuperHeroforId(superhero), new CharacterAppMapper());

        for (CharacterJdbcModel characterComics : characterJdbcModels) {
            CharactersAppJdbcModel charactersAppJdbcModel = new CharactersAppJdbcModel();

            charactersAppJdbcModel.setLast_sync(sh.getLast_sync());
            charactersAppJdbcModel.setCharacter(characterComics.getCharacter());

            List<ComicsAppJdbcModel> comicsAppJdbcModels = jdbcTemplate.query(searhchCharacteComicQuery,
                            pathVariableRest.getPathVariableCharacterComic(superhero, characterComics.getCharacter()),
                            new CharacterComicsAppMapper())
                    .stream().map(CharacterComicJdbcModel::toDomain).collect(Collectors.toList());

            charactersAppJdbcModel.setComics(comicsAppJdbcModels);
            charactersAppJdbcModelList.add(charactersAppJdbcModel);

        }

        return charactersAppJdbcModelList;
    }


}

package com.albo.marvel.adapter.impl;

import com.albo.marvel.adapter.impl.exception.ExeptionRest;
import com.albo.marvel.adapter.impl.model.SuperHeroAppJdbcModel;
import com.albo.marvel.adapter.impl.model.SuperHeroAppMapper;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.config.SqlReader;
import com.albo.marvel.domain.SuperHeroApp;
import com.albo.marvel.domain.Superhero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class SuperheroImplAdapter implements SuperheroSearchRepository {

    private RestTemplate marvelClientRest;

    PathVariableRest pathVariableRest;

    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String PATH_SEARCH_SUPERHERO = "sql/superheroforid.sql";

    private static final String PATH_SAVE_SUPERHERO = "sql/savesuperhero.sql";

    private static final String PATH_SEARCH_SUPERHERO_ALL = "sql/superheroall.sql";
    private static final String PATH_UPDATE_SUPERHERO = "sql/updatesuperhero.sql";

    private String saveSuperHeroQuery;
    private String searchSuperHeroforIdQuery;
    private String searchSuperHeroforAllQuery;
    private String updateSuperHeroQuery;

    private MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    SuperheroImplAdapter(RestTemplate marvelClientRest, PathVariableRest pathVariableRest, NamedParameterJdbcTemplate jdbcTemplate) {
        this.marvelClientRest = marvelClientRest;
        this.pathVariableRest = pathVariableRest;
        this.jdbcTemplate = jdbcTemplate;
        this.searchSuperHeroforIdQuery = SqlReader.readSql(PATH_SEARCH_SUPERHERO);
        this.saveSuperHeroQuery = SqlReader.readSql(PATH_SAVE_SUPERHERO);
        this.updateSuperHeroQuery = SqlReader.readSql(PATH_UPDATE_SUPERHERO);
        this.searchSuperHeroforAllQuery = SqlReader.readSql(PATH_SEARCH_SUPERHERO_ALL);
    }

    @Override
    public Superhero superheroRepository(String name) {

        Superhero superhero = null;
        try {
            superhero = marvelClientRest.getForObject("http://gateway.marvel.com/v1/public/characters?name={name}&ts={ts}&apikey={apikey}&hash={hash}", Superhero.class, pathVariableRest.getPathVariableSuperHero(name));
        } catch (Exception ex) {
            log.error("Error al Ejecutar Request ", ex.getMessage());
            throw new ExeptionRest(ex);
        }

        return superhero;
    }

    @Override
    public void saveOrUpdateSuperHero(Superhero superhero) {

        try {
            jdbcTemplate.queryForObject(searchSuperHeroforIdQuery, pathVariableRest.getPathVariableSuperHeroforId(superhero), new SuperHeroAppMapper());
            jdbcTemplate.update(updateSuperHeroQuery,sqlParameterSource);
        } catch (Exception e) {
            log.info("No existe Datos con ese ID, Regstramos Super Heroe {} ", superhero);
            saveSupeHero(superhero);
        }

    }

    @Override
    public SuperHeroAppJdbcModel superheroIdRepository(Superhero superhero) {
        SuperHeroAppJdbcModel superHeroAppJdbcModel =jdbcTemplate.queryForObject(searchSuperHeroforIdQuery, pathVariableRest.getPathVariableSuperHeroforId(superhero), new SuperHeroAppMapper());
        return superHeroAppJdbcModel;
    }

    @Override
    public List<SuperHeroApp> superHeroAllRepository() {

        return jdbcTemplate.query(searchSuperHeroforAllQuery, new SuperHeroAppMapper())
                .stream().map(SuperHeroAppJdbcModel::toDomain).collect(Collectors.toList());
    }

    private void saveSupeHero(Superhero superhero) {

        sqlParameterSource.addValue("id", superhero.getData().getResults().get(0).getId());
        sqlParameterSource.addValue("name", superhero.getData().getResults().get(0).getName());
        sqlParameterSource.addValue("description", superhero.getData().getResults().get(0).getDescription());

        jdbcTemplate.update(saveSuperHeroQuery,sqlParameterSource);
    }


}

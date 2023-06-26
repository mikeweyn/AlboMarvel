package com.albo.marvel.adapter.impl;

import com.albo.marvel.adapter.impl.exception.ExeptionRest;
import com.albo.marvel.adapter.impl.model.*;
import com.albo.marvel.aplication.port.on.ColaboratorsRepository;
import com.albo.marvel.aplication.port.on.SuperheroSearchRepository;
import com.albo.marvel.config.SqlReader;
import com.albo.marvel.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ColaboratorsImplAdapter implements ColaboratorsRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private static final String PATH_CREATE_COLABOLATORS = "sql/colabolators.sql";

    private static final String PATH_COLABOLATORS_FOR_ID = "sql/colaboratorsforid.sql";

    private String createcColabolatorsQuery;

    private String colabolatorsForIdQuery;

    private RestTemplate marvelClientRest;

    PathVariableRest pathVariableRest;

    SuperheroSearchRepository superheroSearchRepository;

    private MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    public ColaboratorsImplAdapter(NamedParameterJdbcTemplate jdbcTemplate, PathVariableRest pathVariableRest, RestTemplate marvelClientRest, SuperheroSearchRepository superheroSearchRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.createcColabolatorsQuery = SqlReader.readSql(PATH_CREATE_COLABOLATORS);
        this.colabolatorsForIdQuery = SqlReader.readSql(PATH_COLABOLATORS_FOR_ID);
        this.pathVariableRest = pathVariableRest;
        this.marvelClientRest = marvelClientRest;
        this.superheroSearchRepository = superheroSearchRepository;
    }

    @Override
    public Colaborators getColaborators(Superhero superhero) {

        Colaborators colaborators = null;
        try {
            colaborators = marvelClientRest.getForObject("https://gateway.marvel.com:443/v1/public/comics?format=comic&formatType=comic&noVariants=true&characters={characters}&ts={ts}&apikey={apikey}&hash={hash}", Colaborators.class, pathVariableRest.getPathVariable(superhero));
            saveColaborators(colaborators, superhero);
        } catch (Exception ex) {
            log.error("Error al Ejecutar Request ", ex.getMessage());
            throw new ExeptionRest(ex);
        }

        return colaborators;
    }

    private void saveColaborators(Colaborators colaborators, Superhero superhero) {

        List<ResultsColaborators> resultsColaborators = colaborators.getData().getResults();
        List<Creators> creators = resultsColaborators.stream().map(r -> new Creators(r.getCreators().getItems())).collect(Collectors.toList());

        for (Creators cr : creators) {

            for (Items it : cr.getItems()) {
                sqlParameterSource.addValue("id", superhero.getData().getResults().get(0).getId());
                sqlParameterSource.addValue("name", it.getName());
                sqlParameterSource.addValue("role", it.getRole());

                jdbcTemplate.update(createcColabolatorsQuery, sqlParameterSource);
            }
        }

    }

    @Override
    public ColaboratorsAppJdbcModel getColaboratorsApp(Superhero superhero) {

        SuperHeroAppJdbcModel sh = superheroSearchRepository.superheroIdRepository(superhero);
        List<ColaboratorsJdbcModel> colaboratorsJdbcModel = jdbcTemplate.query(colabolatorsForIdQuery, pathVariableRest.getPathVariableSuperHeroforId(superhero), new ColaboratorsAppMapper());
        ColaboratorsAppJdbcModel colaboratorsAppJdbcModel = getBuilColaboratorsAppJdbcModel(sh, colaboratorsJdbcModel);

        return colaboratorsAppJdbcModel;
    }

    private ColaboratorsAppJdbcModel getBuilColaboratorsAppJdbcModel(SuperHeroAppJdbcModel sh, List<ColaboratorsJdbcModel> colaboratorsJdbcModel) {

        List<RolesJdbcModel> editor = colaboratorsJdbcModel.stream()
                .filter(c -> c.getRole().equals("editor"))
                .map(ColaboratorsJdbcModel::toDomain)
                .collect(Collectors.toList());

        List<RolesJdbcModel> writers = colaboratorsJdbcModel.stream()
                .filter(c -> c.getRole().equals("writer"))
                .map(ColaboratorsJdbcModel::toDomain)
                .collect(Collectors.toList());

        List<RolesJdbcModel> colorist = colaboratorsJdbcModel.stream()
                .filter(c -> c.getRole().equals("colorist"))
                .map(ColaboratorsJdbcModel::toDomain)
                .collect(Collectors.toList());

        return ColaboratorsAppJdbcModel.builder()
                .last_sync(sh.getLast_sync())
                .editors(editor)
                .writers(writers)
                .colorists(colorist)
                .build();
    }


}

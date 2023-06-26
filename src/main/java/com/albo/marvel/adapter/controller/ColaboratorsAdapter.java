package com.albo.marvel.adapter.controller;

import com.albo.marvel.adapter.controller.model.ColaboratorsRest;
import com.albo.marvel.adapter.impl.model.ColaboratorsAppJdbcModel;
import com.albo.marvel.aplication.port.in.ColaboratorsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("marvel")
public class ColaboratorsAdapter {

    ColaboratorsQuery colaboratorsQuery;

    public ColaboratorsAdapter(ColaboratorsQuery colaboratorsQuery){
        this.colaboratorsQuery = colaboratorsQuery;
    }

    @GetMapping(value = "/colaborators/{characters}")
    public ColaboratorsRest getColaborators(@PathVariable("characters") String characters) {

        ColaboratorsAppJdbcModel colaborators =  colaboratorsQuery.execute(characters);
        ColaboratorsRest colaboratorsRest = ColaboratorsRest.toColaboratorsRest(colaborators);

        return colaboratorsRest;
    }
}

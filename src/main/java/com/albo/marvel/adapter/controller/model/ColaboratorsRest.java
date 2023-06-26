package com.albo.marvel.adapter.controller.model;


import com.albo.marvel.adapter.impl.model.ColaboratorsAppJdbcModel;
import com.albo.marvel.adapter.impl.model.RolesJdbcModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;
import java.util.List;

@Value
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ColaboratorsRest {

    Timestamp last_sync;
    List<RolesJdbcModel> editors;
    List<RolesJdbcModel> writers;
    List<RolesJdbcModel> colorists;


    public static ColaboratorsRest toColaboratorsRest(ColaboratorsAppJdbcModel colaborators){
        return ColaboratorsRest.builder()
                .last_sync(colaborators.getLast_sync())
                .editors(colaborators.getEditors())
                .writers(colaborators.getWriters())
                .colorists(colaborators.getColorists())
                .build();
    }

}

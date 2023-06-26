package com.albo.marvel.adapter.impl.model;

import com.albo.marvel.domain.SuperHeroApp;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class SuperHeroAppJdbcModel {

    Long id;
    String name;
    String description;
    Timestamp last_sync;

    public SuperHeroApp toDomain(){
        return SuperHeroApp.builder()
                .id(id)
                .name(name)
                .last_sync(last_sync)
                .build();
    }

}

package com.albo.marvel.adapter.impl.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterComicJdbcModel {

    String comics;

    public ComicsAppJdbcModel toDomain(){
        return ComicsAppJdbcModel.builder()
                .name(comics)
                .build();
    }

}

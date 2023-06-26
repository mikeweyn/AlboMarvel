package com.albo.marvel.adapter.impl.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColaboratorsJdbcModel {

    Long id;
    String name;
    String role;

    public RolesJdbcModel toDomain(){
        return RolesJdbcModel.builder()
                .name(name)
                .build();
    }


}

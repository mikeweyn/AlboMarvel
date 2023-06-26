package com.albo.marvel.adapter.impl.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class ColaboratorsAppJdbcModel {

    Timestamp last_sync;
    List<RolesJdbcModel> editors;
    List<RolesJdbcModel> writers;
    List<RolesJdbcModel> colorists;


}

package com.albo.marvel.adapter.impl.model;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperHeroAppMapper implements RowMapper<SuperHeroAppJdbcModel> {

    public SuperHeroAppJdbcModel mapRow(ResultSet rs, int rowNum) throws SQLException{
        return SuperHeroAppJdbcModel.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .last_sync(rs.getTimestamp("last_sync"))
                .build();
    }
}

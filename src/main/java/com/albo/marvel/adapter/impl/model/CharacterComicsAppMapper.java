package com.albo.marvel.adapter.impl.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterComicsAppMapper implements RowMapper<CharacterComicJdbcModel> {

    @Override
    public CharacterComicJdbcModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CharacterComicJdbcModel.builder()
                .comics(rs.getString("comics"))
                .build();
    }
}

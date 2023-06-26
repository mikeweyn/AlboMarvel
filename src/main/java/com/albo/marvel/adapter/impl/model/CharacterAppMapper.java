package com.albo.marvel.adapter.impl.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterAppMapper implements RowMapper<CharacterJdbcModel> {

    @Override
    public CharacterJdbcModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CharacterJdbcModel.builder()
                .character(rs.getString("character"))
                .build();
    }
}
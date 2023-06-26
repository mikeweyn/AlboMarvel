package com.albo.marvel.adapter.impl.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColaboratorsAppMapper implements RowMapper<ColaboratorsJdbcModel> {

    @Override
    public ColaboratorsJdbcModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ColaboratorsJdbcModel.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .role(rs.getString("role"))
                .build();
    }
}

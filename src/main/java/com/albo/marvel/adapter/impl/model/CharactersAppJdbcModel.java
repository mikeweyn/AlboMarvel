package com.albo.marvel.adapter.impl.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharactersAppJdbcModel {

    Timestamp last_sync;
    String character;
    List<ComicsAppJdbcModel> comics;
}

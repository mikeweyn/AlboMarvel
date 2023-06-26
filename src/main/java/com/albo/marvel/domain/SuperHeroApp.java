package com.albo.marvel.domain;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuperHeroApp {

    Long id;
    String name;
    String description;
    Timestamp last_sync;

}

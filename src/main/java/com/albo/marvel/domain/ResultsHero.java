package com.albo.marvel.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultsHero {

    long id;
    String name;
    String description;

}

package com.albo.marvel.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataHero {

    List<ResultsHero> results;
}

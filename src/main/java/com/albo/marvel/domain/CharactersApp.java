package com.albo.marvel.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CharactersApp {

    Long id;
    Long id_comic;
    String character;
    String comics;
}

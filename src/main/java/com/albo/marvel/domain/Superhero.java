package com.albo.marvel.domain;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Superhero {

    String attributionHTML;
    DataHero data;

}

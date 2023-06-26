package com.albo.marvel.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ColaboratorsApp {

    Long id;
    String name;
    String role;

}



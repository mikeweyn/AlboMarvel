package com.albo.marvel.aplication.port.on;

import com.albo.marvel.adapter.impl.model.ColaboratorsAppJdbcModel;
import com.albo.marvel.domain.Colaborators;
import com.albo.marvel.domain.Superhero;

public interface ColaboratorsRepository {

    Colaborators getColaborators(Superhero superhero);

    ColaboratorsAppJdbcModel getColaboratorsApp(Superhero superhero);
}

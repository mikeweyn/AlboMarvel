package com.albo.marvel.aplication.port.in;

import com.albo.marvel.adapter.impl.model.ColaboratorsAppJdbcModel;

public interface ColaboratorsQuery {

    ColaboratorsAppJdbcModel execute(String name);
}

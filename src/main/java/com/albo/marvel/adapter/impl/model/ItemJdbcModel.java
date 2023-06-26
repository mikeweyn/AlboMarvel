package com.albo.marvel.adapter.impl.model;

import com.albo.marvel.domain.Creators;
import com.albo.marvel.domain.Items;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemJdbcModel {

    List<Items> items;

    public Creators toDomain(){
        return Creators.builder()
                .items(items)
                .build();
    }
}

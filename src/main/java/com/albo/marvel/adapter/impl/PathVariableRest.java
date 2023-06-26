package com.albo.marvel.adapter.impl;

import com.albo.marvel.config.EncrycpKey;
import com.albo.marvel.domain.Superhero;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PathVariableRest {

    @Value("${marvel.public.key}")
    private String publicKey;

    @Value("${marvel.private.key}")
    private String privateKey;
    @Value("${marvel.ts.key}")
    private String tsKey;

    private EncrycpKey encrycpKey = new EncrycpKey();


    public Map<String, String> getPathVariable(Superhero superhero){


        Map<String, String> pathvariable = new HashMap<String, String>();
        pathvariable.put("characters", String.valueOf(superhero.getData().getResults().get(0).getId()));
        pathvariable.put("ts", tsKey);
        pathvariable.put("apikey", publicKey);
        pathvariable.put("hash", encrycpKey.encrycpCode(tsKey, privateKey, publicKey));

        return pathvariable;
    }

    public Map<String, String> getPathVariableSuperHero(String name){

        Map<String, String> pathvariable = new HashMap<String,String>();
        pathvariable.put("name", name);
        pathvariable.put("ts", tsKey);
        pathvariable.put("apikey", publicKey);
        pathvariable.put("hash", encrycpKey.encrycpCode(tsKey, privateKey, publicKey));

        return pathvariable;
    }


    public  Map<String, String> getPathVariableSuperHeroforId(Superhero superhero) {
        Map<String, String> pathvariable = new HashMap<String,String>();
        pathvariable.put("id", String.valueOf(superhero.getData().getResults().get(0).getId()));
        return pathvariable;
    }

    public Map<String,?> getPathVariableCharacterComic(Superhero superhero, String character) {
        Map<String, String> pathvariable = new HashMap<String,String>();
        pathvariable.put("id", String.valueOf(superhero.getData().getResults().get(0).getId()));
        pathvariable.put("character", character);
        return pathvariable;
    }
}

package com.web.gruposti.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CharacterResponse {
    private String type;
    private String action;
    private Data data;

    @lombok.Data
    @ToString
    public static class Data {
        private int items;
        private List<MarvelCharacter> characters;
    }


}

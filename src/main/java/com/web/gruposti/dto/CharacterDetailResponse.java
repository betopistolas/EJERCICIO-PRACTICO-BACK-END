package com.web.gruposti.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class CharacterDetailResponse {
    private String type;
    private String action;
    private Data data;


    @lombok.Data
    @ToString
    public static class Data {
        private int id;
        private String name;
        private String description;
        private String image;
        private List<Comic> comics;

        @lombok.Data
        @ToString
        public static class Comic {
            private String name;
        }
    }

}

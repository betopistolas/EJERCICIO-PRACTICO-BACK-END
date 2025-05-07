package com.web.gruposti.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MarvelCharacter {
    private int id;
    private String name;
    private String description;
    private String image;
}

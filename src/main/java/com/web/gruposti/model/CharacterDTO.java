package com.web.gruposti.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CharacterDTO {
    private int id;
    private String name;
    private String description;
    private String image;
}

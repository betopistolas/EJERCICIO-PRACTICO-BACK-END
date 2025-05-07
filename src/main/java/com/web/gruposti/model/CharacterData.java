package com.web.gruposti.model;

import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
public class CharacterData {
    private int items;
    private List<CharacterDTO> characters;
}

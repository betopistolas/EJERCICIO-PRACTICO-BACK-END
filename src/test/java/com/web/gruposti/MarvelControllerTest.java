package com.web.gruposti;

import com.web.gruposti.controller.MarvelController;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.service.MarvelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MarvelController.class)
class MarvelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarvelService marvelService; // Esta clase debe estar anotada con @Service

    @Test
    void testGetCharactersEndpoint() throws Exception {
        MarvelCharacter character = new MarvelCharacter();
        character.setId(1);
        character.setName("Iron Man");
        character.setDescription("Genius billionaire");
        character.setImage("http://example.com/ironman.jpg");

        when(marvelService.getCharacters()).thenReturn(List.of(character));

        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("SUCCESS"))
                .andExpect(jsonPath("$.data.items").value(1))
                .andExpect(jsonPath("$.data.characters[0].name").value("Iron Man"));
    }
}

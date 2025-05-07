package com.web.gruposti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.service.MarvelService;
import com.web.gruposti.util.MSProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarvelServiceTest {

    @InjectMocks
    private MarvelService marvelService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MSProperties msProperties;

    @Test
    void testGetCharacters_success() throws Exception {
        // 1. Simular el endpoint de la propiedad
        when(msProperties.getApiCharacters()).thenReturn("http://fake-url");

        // 2. Crear respuesta simulada
        String json = """
        {
            "data": {
                "results": [
                    {
                        "id": 1,
                        "name": "Spider-Man",
                        "description": "Friendly neighborhood hero",
                        "thumbnail": {
                            "path": "http://image.com/spiderman",
                            "extension": "jpg"
                        }
                    }
                ]
            }
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode mockResponse = mapper.readTree(json);

        // 3. Simular la respuesta del restTemplate
        when(restTemplate.getForEntity(anyString(), eq(JsonNode.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // 4. Ejecutar y validar
        var result = marvelService.getCharacters();

        assertEquals(1, result.size());
        assertEquals("Spider-Man", result.get(0).getName());
        assertEquals("http://image.com/spiderman.jpg", result.get(0).getImage());
    }
}
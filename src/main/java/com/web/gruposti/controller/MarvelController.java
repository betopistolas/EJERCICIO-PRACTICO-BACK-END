package com.web.gruposti.controller;

import com.web.gruposti.dto.CharacterResponse;
import com.web.gruposti.dto.ErrorResponse;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.service.MarvelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "Marvel Characters", description = "API para obtener personajes de Marvel")
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    @Operation(
            summary = "Obtener personajes de Marvel",
            description = "Retorna una lista de hasta 25 personajes desde la API de Marvel",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Personajes obtenidos correctamente"),
                    @ApiResponse(responseCode = "500", description = "Error al conectarse con la API de Marvel")
            }
    )
    @GetMapping("/characters")
    public ResponseEntity getCharacters() {
        try {

            List<MarvelCharacter> characterList = marvelService.getCharacters();
            CharacterResponse response = new CharacterResponse();
            response.setType("SUCCESS");
            response.setAction("CONTINUE");

            CharacterResponse.Data data = new CharacterResponse.Data();
            data.setItems(characterList.size());
            data.setCharacters(characterList);
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

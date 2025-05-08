package com.web.gruposti.controller;

import com.web.gruposti.dto.CharacterDetailResponse;
import com.web.gruposti.dto.CharacterResponse;
import com.web.gruposti.dto.ErrorResponse;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.service.MarvelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@RestController
@RequestMapping("/characters")
@Tag(name = "Marvel Characters", description = "API para obtener personajes de Marvel")
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    @Operation(
            summary = "Listar personajes de Marvel",
            description = "Obtiene una lista de personajes desde la API de Marvel con un límite configurable (entre 1 y 100)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Personajes obtenidos correctamente"),
                    @ApiResponse(responseCode = "400", description = "Límite inválido")
            }
    )
    @GetMapping
    public ResponseEntity getCharacters(@Parameter(description = "Número máximo de personajes a retornar (1-100)", example = "25")
                                            @RequestParam(defaultValue = "20") int limit) {
        try {

            if (limit < 1 || limit > 100) {
                ErrorResponse error = new ErrorResponse(
                        "INVALID_LIMIT", "Provided value for limit param is invalid."
                );
                return ResponseEntity.badRequest().body(error);
            }
            List<MarvelCharacter> characters = marvelService.getCharacters(limit);
            CharacterResponse.Data data = new CharacterResponse.Data();
            data.setItems(characters.size());
            data.setCharacters(characters);

            CharacterResponse response = new CharacterResponse();
            response.setType("SUCCESS");
            response.setAction("CONTINUE");
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @Operation(
            summary = "Obtener detalle de un personaje",
            description = "Retorna el detalle del personaje especificado por ID, incluyendo su nombre, descripción, imagen y comics relacionados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalle del personaje encontrado"),
                    @ApiResponse(responseCode = "404", description = "Personaje no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity getCharacterDetail(
            @Parameter(description = "ID del personaje a consultar", example = "1011334") @PathVariable int id) {
        return ResponseEntity.ok(marvelService.getCharacterDetail(id));
    }
}

package com.web.gruposti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.gruposti.dto.CharacterDetailResponse;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.exception.MarvelApiException;
import com.web.gruposti.service.MarvelService;
import com.web.gruposti.util.MSProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelServiceImpl implements MarvelService {

    private static final Log log = LogFactory.getLog(MarvelServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private final MSProperties p;


    @Autowired
    public MarvelServiceImpl(RestTemplate restTemplate, MSProperties p) {
        this.restTemplate = restTemplate;
        this.p = p;
    }

    public List<MarvelCharacter> getCharacters(int limit) {

        String marvelApiUrl = p.getApiCharacters() +
                "?limit=" + limit +
                "&ts="+p.getTs()+
                "&apikey="+p.getPublicKey()+
                "&hash="+ p.getHash();

        log.info("CALL API="+marvelApiUrl);

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(marvelApiUrl, JsonNode.class);
            JsonNode results = response.getBody().get("data").get("results");

            log.info("CALL API RESPONSE OK");
            List<MarvelCharacter> characters = new ArrayList<>();
            for (JsonNode node : results) {
                MarvelCharacter character = new MarvelCharacter();
                character.setId(node.get("id").asInt());
                character.setName(node.get("name").asText());
                character.setDescription(node.get("description").asText());
                JsonNode thumbnail = node.get("thumbnail");
                character.setImage(thumbnail.get("path").asText() + "." + thumbnail.get("extension").asText());
                characters.add(character);
            }
            return characters;
        } catch (Exception e) {
            log.error("Error al obtener lista de personajes de Marvel. error="+ e.getMessage());
            throw new MarvelApiException("404","Error al obtener personajes de Marvel", e);
        }

    }

    public CharacterDetailResponse getCharacterDetail(int id) {

        String marvelApiUrl = p.getApiCharacters() + "/"+id+"?ts="+p.getTs()+"&apikey="+p.getPublicKey()+"&hash="+ p.getHash();
        log.info("CALL API="+marvelApiUrl);

        CharacterDetailResponse res = new CharacterDetailResponse();
        res.setType("SUCCESS");
        res.setAction("CONTINUE");

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(marvelApiUrl, JsonNode.class);
            JsonNode result = response.getBody().get("data").get("results").get(0);
            log.info("CALL API RESPONSE OK");
            CharacterDetailResponse.Data data = new CharacterDetailResponse.Data();
            data.setId(result.get("id").asInt());
            data.setName(result.get("name").asText());
            data.setDescription(result.get("description").asText());
            JsonNode thumbnail = result.get("thumbnail");
            data.setImage(thumbnail.get("path").asText() + "." + thumbnail.get("extension").asText());

            List<CharacterDetailResponse.Data.Comic> comics = new ArrayList<>();
            for (JsonNode comicNode : result.get("comics").get("items")) {
                CharacterDetailResponse.Data.Comic comic = new CharacterDetailResponse.Data.Comic();
                comic.setName(comicNode.get("name").asText());
                comics.add(comic);
            }
            data.setComics(comics);
            res.setData(data);

        } catch (HttpClientErrorException e) {
            log.error("Error al obtener personaje de Marvel. error="+ e.getMessage());
            String code="400";
            String message ="Error desconocido al parsear respuesta de error Marvel";
            try {
                JsonNode errorBody = objectMapper.readTree(e.getResponseBodyAsString());
                code = errorBody.get("code").asText();
                message = errorBody.get("status").asText();
                throw new MarvelApiException(code, message, e);
            } catch (Exception parseEx) {
                throw new MarvelApiException(code,message, e);
            }
        }
        return res;
    }
}

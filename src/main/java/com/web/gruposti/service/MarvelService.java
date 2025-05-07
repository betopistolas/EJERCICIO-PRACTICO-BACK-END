package com.web.gruposti.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.exception.MarvelApiException;
import com.web.gruposti.util.MSProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelService {



    private static final Log log = LogFactory.getLog(MarvelService.class);
    private final RestTemplate restTemplate;
    private final MSProperties p;


    @Autowired
    public MarvelService(RestTemplate restTemplate, MSProperties p) {
        this.restTemplate = restTemplate;
        this.p = p;
    }

    public List<MarvelCharacter> getCharacters() {
        String ts = String.valueOf(System.currentTimeMillis());
        String marvelApiUrl = p.getApiCharacters() +"?ts="+p.getTs()+"&apikey="+p.getPublicKey()+"&hash="+ p.getHash();
        log.info("marvelApiUrl="+marvelApiUrl);

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(marvelApiUrl, JsonNode.class);
            JsonNode results = response.getBody().get("data").get("results");

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
            throw new MarvelApiException("Error al obtener personajes de Marvel", e);
        }

    }

}

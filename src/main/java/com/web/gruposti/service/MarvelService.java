package com.web.gruposti.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.gruposti.dto.CharacterDetailResponse;
import com.web.gruposti.dto.MarvelCharacter;
import com.web.gruposti.exception.MarvelApiException;
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


public interface MarvelService {

    public List<MarvelCharacter> getCharacters(int limit);

    public CharacterDetailResponse getCharacterDetail(int id);
}

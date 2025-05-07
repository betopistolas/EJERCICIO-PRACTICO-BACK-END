package com.web.gruposti.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MSProperties {

    @Value("${public_key}")
    private String publicKey;

    @Value("${public_key}")
    private String privateKey;

    @Value("${ts}")
    private String ts;

    @Value("${hash}")
    private String hash;

    @Value("${api_characters}")
    private String apiCharacters;


    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getApiCharacters() {
        return apiCharacters;
    }

    public String getTs() {
        return ts;
    }

    public String getHash() {
        return hash;
    }
}

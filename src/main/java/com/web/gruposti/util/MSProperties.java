package com.web.gruposti.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
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

}

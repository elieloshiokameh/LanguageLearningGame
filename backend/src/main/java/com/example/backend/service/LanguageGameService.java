package com.example.backend.service;

import com.example.backend.domain.Language;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class LanguageGameService {
    private final RestTemplate restTemplate;
    private static final String TRANSLATE_API_BASE_URL = "https://libretranslate.com/";

    private final Map<String, String> supportedLanguagesByCode = new HashMap<>();

    public LanguageGameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initLanguages() {
        Language[] languages = restTemplate.getForObject(TRANSLATE_API_BASE_URL + "languages", Language[].class);

        for (Language l : languages) {
            supportedLanguagesByCode.put(l.getCode(), l.getName());
        }
    }

    public Map<String, String> getSupportedLanguagesByCode() {
        return supportedLanguagesByCode;
    }
}

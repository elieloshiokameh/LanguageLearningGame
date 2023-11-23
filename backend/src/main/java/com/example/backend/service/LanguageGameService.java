package com.example.backend.service;

import com.example.backend.domain.Language;
import com.example.backend.domain.TranslationResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Service
public class LanguageGameService {
    private final RestTemplate restTemplate;
    private final Random random = new Random();
    private static final String TRANSLATE_API_BASE_URL = "https://translate.terraprint.co/";
    private static final String WORDLIST_FILENAME = "english-word-list-nouns.csv";

    private final Map<String, String> supportedLanguagesByCode = new HashMap<>();
    private final List<String> wordList = new ArrayList<>();

    public LanguageGameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() {
        // Read in words
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(WORDLIST_FILENAME)) {
            if (is == null) {
                throw new FileNotFoundException(WORDLIST_FILENAME + " not found");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // Skip the first 4 lines (headers)
            for (int i = 0; i < 4; i++) {
                br.readLine();
            }

            // Read the rest of the lines and extract the second field
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.split(";")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        // Initialise languages list
        Language[] languages = restTemplate.getForObject(TRANSLATE_API_BASE_URL + "languages", Language[].class);

        for (Language l : languages) {
            supportedLanguagesByCode.put(l.getCode(), l.getName());
        }
    }

    public Map<String, String> getSupportedLanguagesByCode() {
        return supportedLanguagesByCode;
    }

    public List<String> getRandomWordInLanguages(String... languages) {
        String randomWord = wordList.get(random.nextInt(wordList.size()));

        List<String> result = new ArrayList<>();

        for (String l : languages) {
            if (l.equals("en")) {
                result.add(randomWord);
            } else {
                result.add(translateToLanguage(randomWord, l));
            }
        }

        return result;
    }

    private String translateToLanguage(String word, String languageCode) {
        Map<String, String> apiRequestBody = Map.of(
                "q", word, "source",
                "en", "target", languageCode
        );

        ResponseEntity<TranslationResponse> response = restTemplate.postForEntity(
                TRANSLATE_API_BASE_URL + "/translate", apiRequestBody, TranslationResponse.class
        );

        if (response.getStatusCode().is4xxClientError()) {
            System.err.println("Error with translation request " + apiRequestBody + ": " + response.getBody().getError());
            return null;
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.err.println("Error with translation request " + apiRequestBody + ": " + response);
        }

        return response.getBody().getTranslatedText();
    }
}

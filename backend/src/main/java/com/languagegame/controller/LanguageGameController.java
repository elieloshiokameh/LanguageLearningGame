package com.languagegame.controller;

import com.languagegame.service.LanguageGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LanguageGameController {

    private final LanguageGameService languageGameService;

    public LanguageGameController(LanguageGameService languageGameService) {
        this.languageGameService = languageGameService;
    }

    @GetMapping("/languages")
    public ResponseEntity<Map<String, String>> getLanguages() {
        return ResponseEntity.ok(languageGameService.getSupportedLanguagesByCode());
    }

    @GetMapping("/{languageCode1}/{languageCode2}/randomWord")
    public ResponseEntity<List<String>> getRandomWord(@PathVariable String languageCode1, @PathVariable String languageCode2) {
        Map<String, String> supportedLanguages = languageGameService.getSupportedLanguagesByCode();
        if (!supportedLanguages.containsKey(languageCode1) || !supportedLanguages.containsKey(languageCode2)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(languageGameService.getRandomWordInLanguages(languageCode1, languageCode2));
    }
}

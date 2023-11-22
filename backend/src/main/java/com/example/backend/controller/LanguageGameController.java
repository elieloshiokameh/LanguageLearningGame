package com.example.backend.controller;

import com.example.backend.service.LanguageGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
}

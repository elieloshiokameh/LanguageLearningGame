package com.languagegame.controller;

import com.languagegame.domain.PlayedGameDTO;
import com.languagegame.domain.StatisticsDTO;
import com.languagegame.security.service.UserDetailsImpl;
import com.languagegame.service.LanguageGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://66.241.125.169:443", maxAge = 3600, allowCredentials = "true")
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

    @PostMapping("/game")
    public ResponseEntity<?> postGameStatistics(Authentication authentication, @RequestBody PlayedGameDTO playedGameDTO) {
        languageGameService.addGameStatistics(
                playedGameDTO,
                ((UserDetailsImpl) authentication.getPrincipal()).getUser()
        );


        return ResponseEntity.ok("Game recorded successfully");
    }

    @GetMapping("/games")
    public ResponseEntity<StatisticsDTO> getGameStatistics(Authentication authentication) {
        StatisticsDTO statistics = languageGameService.getGameStatistics(((UserDetailsImpl) authentication.getPrincipal()).getUser());

        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/games/{questionLanguage}/{answerLanguage}")
    public ResponseEntity<StatisticsDTO> getGameStatistics(Authentication authentication, @PathVariable String questionLanguage, @PathVariable String answerLanguage) {
        StatisticsDTO statistics = languageGameService.getGameStatistics(
                ((UserDetailsImpl) authentication.getPrincipal()).getUser(),
                questionLanguage,
                answerLanguage
        );

        return ResponseEntity.ok(statistics);
    }
}

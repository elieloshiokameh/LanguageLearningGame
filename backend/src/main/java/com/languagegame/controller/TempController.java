package com.languagegame.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TempController {
    @GetMapping("/test")
    public ResponseEntity<String> getTest(Principal principal, Authentication authentication) {
        System.out.println("principal = " + principal);
        System.out.println("authentication = " + authentication);
        return ResponseEntity.ok("test");
    }
    @GetMapping("/test2")
    public ResponseEntity<String> getTest2() {
        return ResponseEntity.ok("test2");
    }
}

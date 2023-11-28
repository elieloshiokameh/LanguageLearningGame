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
    @GetMapping("/authtest")
    public ResponseEntity<String> getTest(Principal principal, Authentication authentication) {
        System.out.println("principal = " + principal);
        System.out.println("authentication = " + authentication);
        return ResponseEntity.ok("test");
    }
    @GetMapping("/tmp_login")
    public ResponseEntity<String> getTest2() {
        return ResponseEntity.ok("""
                <h2>Google login</h2>
                <a href="/oauth2/authorization/google">Login</a>
                
                <h2>Login Form</h2>
                        
                <form id="loginForm">
                    <label for="username">Email:</label>
                    <input type="text" id="username" name="username" required>
                
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                
                    <button type="button" onclick="submitLogin()">Login</button>
                </form>
                
                <h2>Signup Form</h2>
                
                <form id="signupForm">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                
                    <label for="signupUsername">Username:</label>
                    <input type="text" id="signupUsername" name="signupUsername" required>
                
                    <label for="signupPassword">Password:</label>
                    <input type="password" id="signupPassword" name="signupPassword" required>
                
                    <button type="button" onclick="submitSignup()">Signup</button>
                </form>
                
                <h2>Signout</h2>
                
                <button type="button" onclick="signout()">Signout</button>
                
                <p id="responseText"></p>
                
                
                <script>
                    function submitLogin() {
                        sendRequest('/api/auth/signin', {
                            username: document.getElementById("username").value,
                            password: document.getElementById("password").value
                        });
                    }
                
                    function submitSignup() {
                        sendRequest('/api/auth/signup', {
                            email: document.getElementById("email").value,
                            username: document.getElementById("signupUsername").value,
                            password: document.getElementById("signupPassword").value
                        });
                    }
                
                    function signout() {
                        sendRequest('/api/auth/signout', {});
                    }
                
                    function sendRequest(url, data) {
                        var jsonData = JSON.stringify(data);
                
                        fetch(url, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: jsonData
                        })
                        .then(res => res.text())
                        .then(t => {
                            document.getElementById("responseText").innerText = "Server Response: " + t;
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                    }
                </script>
                """);
    }
}

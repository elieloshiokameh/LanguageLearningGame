package com.languagegame.controller;

import com.languagegame.domain.LoginRequest;
import com.languagegame.domain.SignupRequest;
import com.languagegame.domain.User;
import com.languagegame.repository.RoleRepository;
import com.languagegame.repository.UserRepository;
import com.languagegame.security.jwt.JwtUtils;
import com.languagegame.security.service.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/*
    Regarding use of creating classes solely for use as parameters and return values,
    this is because apparently "A good REST API will always respond with JSON", spring converts
    the classes to JSON, hence the gross code
 */

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true") //deny cross origin requests older than 1hr
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    // On logout request, receive logout response (just a string for now)
    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser() {    // Replace jwt cookie with empty cookie
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You have successfully signed out."); //TODO: create responseMessage class for clarity
    }                                                       // ... or maybe not to avoid bloat

    // on successful login request, receive response containing user details (string for now)
    @PostMapping("/signin")
    public ResponseEntity<?> signinUser(@Valid @RequestBody LoginRequest loginDetails) {
        //Create Authentication token from user details and authenticate
        Authentication authToken = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //Create jwt cookie
        UserDetailsImpl userDetails = (UserDetailsImpl) authToken.getPrincipal();
        ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails);

        return ResponseEntity.ok()  // TODO: if anyone sees this and has time, pls write a class that can hold the info in 'body' ...
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body( "ID: " + userDetails.getId() + "\n" +
                        "Username: " + userDetails.getUsername() + "\n" +
                        "Email: " + userDetails.getEmail() + "\n");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(HttpServletResponse response, @Valid @RequestBody SignupRequest registerDetails) throws IOException {
        if (userRepo.existsByUsername(registerDetails.getUsername())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username is taken.");
            return null;
        }
        if (userRepo.existsByEmail(registerDetails.getEmail())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "This Email already has an account.");
            return null;
        }

        // create user
        User user = new User(registerDetails.getUsername(),
                registerDetails.getEmail(),
                encoder.encode(registerDetails.getPassword()));

        //TODO add "roles" to account on creation

        // save user to db
        userRepo.save(user);

        return ResponseEntity.ok("Successfully registered as a user");
    }
}

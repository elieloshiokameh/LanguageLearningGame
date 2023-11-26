package com.languagegame.security;

import com.languagegame.security.jwt.AuthEntryPointJwt;
import com.languagegame.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity //defaults: (securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//@EnableWebSecurity //Uncomment to prioritise Spring Security's web security config
                    // over Spring Boot's web security config
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorisedHandler;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

}

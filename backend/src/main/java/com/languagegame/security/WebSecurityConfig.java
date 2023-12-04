package com.languagegame.security;

import com.languagegame.security.jwt.AuthTokenFilter;
import com.languagegame.security.jwt.AuthEntryPointJwt;
import com.languagegame.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.languagegame.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity //defaults: (securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//@EnableWebSecurity //Uncomment to prioritise Spring Security's web security config
                    // over Spring Boot's web security config
public class WebSecurityConfig {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthEntryPointJwt authEntryPoint;
    @Autowired
    private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    @Autowired
    protected UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticatorManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type")); // Fixes preflight requests
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // passes link through series of security filters
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authEntryPoint)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/error/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
//                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/tmp_login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2.successHandler(oauth2AuthenticationSuccessHandler));
//        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), OAuth2AuthorizationRequestRedirectFilter.class); // need this to authenticate based on just jwt
        return http.build();
    }
}

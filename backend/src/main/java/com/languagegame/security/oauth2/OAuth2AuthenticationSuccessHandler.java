package com.languagegame.security.oauth2;

import com.languagegame.domain.User;
import com.languagegame.repository.UserRepository;
import com.languagegame.security.jwt.JwtUtils;
import com.languagegame.security.service.UserDetailsImpl;
import com.languagegame.security.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("SUCCESS HANDLER authentication = " + authentication);

        String name = ((OAuth2User) authentication.getPrincipal()).getAttribute("name");
        String email = ((OAuth2User)authentication.getPrincipal()).getAttribute("email");
        System.out.println("SUCCESS HANDLER name = " + name);
        System.out.println("SUCCESS HANDLER email = " + email);

        // TODO: Implement email fetch from https://api.github.com/user/emails
        if(email == null){
            //System.out.println("!!!!!!!!!!!!!!! CANT FIND EMAIL !!!!!!!!!!!!!!!!!!!!1");
            email = name + "@unknown.unknown";
        }

        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User(name, email,"OAUTH2");
            userRepo.save(user);
        } else if (!user.getUsername().equals(name) || !user.getPassword().equals("OAUTH2")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not a valid account");
            return;
        }

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        newAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        // Add our new jwt cookie
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        response.addHeader("Set-Cookie", jwtCookie.toString());
        // And remove the oauth2 one!
        ResponseCookie jsCookie = ResponseCookie.from("JSESSIONID", "").maxAge(0).path("/").build();
        response.addHeader("Set-Cookie", jsCookie.toString());

        response.sendRedirect("http://localhost:3000/languageselect");
        // processes redirect cache - but our exception handler overrides this anyway
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}

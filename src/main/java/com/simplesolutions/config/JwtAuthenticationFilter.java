package com.simplesolutions.config;

import com.simplesolutions.services.CustomUserDetailsService;
import com.simplesolutions.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
        We will do the following :-
        1. Get Request Header.
        2. Get the String from Authorization.
        3. Remove 'Bearer ' from string to get the JWT token.
        4. Validate the JWT token
        5. Propagate the filter chain.
         */

        String authorizationString = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (null != authorizationString && authorizationString.startsWith("Bearer ")) {

            jwtToken = authorizationString.substring(7);

            try {
                username = this.jwtUtil.extractUsername(jwtToken);
            } catch (Exception e){
                log.debug("Exception validating JWT token in Filter.");
                e.printStackTrace();
            }

            if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else{
                log.debug("Token is not validated.");
            }

        }

        filterChain.doFilter(request, response);

    }
}

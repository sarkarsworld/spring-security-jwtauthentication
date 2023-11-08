package com.simplesolutions.controllers;

import com.simplesolutions.services.CustomUserDetailsService;
import com.simplesolutions.util.JwtRequest;
import com.simplesolutions.util.JwtResponse;
import com.simplesolutions.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/generatetoken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
        System.out.println(jwtRequest);

        // Authenticate Username and Password.
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        } catch (UsernameNotFoundException e) {
            System.out.println("User Name Not Found Exception ---->");
            e.printStackTrace();
            throw new BadCredentialsException("Bad Credentials");
        } catch (Exception e) {
            System.out.println("Exception ---->");
            e.printStackTrace();
        }


        // Generate token if Username and Password are fine.
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("new jwt token --->"+ token);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}

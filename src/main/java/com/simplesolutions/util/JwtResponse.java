package com.simplesolutions.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyStore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
}

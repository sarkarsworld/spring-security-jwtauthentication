package com.simplesolutions.util;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class JwtRequest {

    private String username;
    private String password;

}

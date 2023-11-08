package com.simplesolutions.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return "this is a private page and will be displayed after authentication.";
    }
}

package com.github.szberes.netbank.backend.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend/users")
public class UserController {

    @RequestMapping("/me")
    public RestUser getCurrentUser(Principal principal) {
        return new RestUser(principal.getName());
    }
}

package com.example.webclient.example.controller;

import com.example.webclient.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public String login() {
        return loginService.login();
    }

    @GetMapping(value = "getLogin")
    public Boolean getLogin() {
        return loginService.getLogin();
    }

}

package com.springboot.blog.controller;

import com.springboot.blog.payloads.LoginDTO;
import com.springboot.blog.payloads.RegisterDTO;
import com.springboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*
        Build Login API
     * here we are using value = {"login","signing"}
     * Because user use some time login or some time signing so we are providing both facility
     * */
    @PostMapping(value = {"login", "signing"})
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);

        //Build Register API
    }

    @PostMapping(value = {"register", "signUp"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        return new ResponseEntity<>(authService.register(registerDTO), HttpStatus.CREATED);
    }
}

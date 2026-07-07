package com.example.demo.controller;

import com.example.demo.dto.AuthDTO;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthDTO request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
}

package com.example.demo.service;

import com.example.demo.dto.AuthDTO;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuditService auditService;

    public Map<String, String> login(AuthDTO credentials) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid email or password");
        }

        User user = userRepository.findByEmail(credentials.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        auditService.logAction("User logged in: " + user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("userId", user.getUserId());
        response.put("organizationId", user.getOrganizationId());
        return response;
    }

    public Map<String, String> register(AuthDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already registered: " + request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        user.setUserId("USR-" + System.currentTimeMillis());
        user.setOrganizationId("CORP-VAULT-01");

        userRepository.save(user);
        auditService.logAction("User registered: " + user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    public void logout() {
        // Stateless JWT - client discards token. No server-side session to invalidate.
    }
}

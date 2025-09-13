package com.lumen.subscription.controllers;

import com.lumen.subscription.entity.User;
import com.lumen.subscription.repo.UserRepository;
import com.lumen.subscription.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRoles());
        return Map.of("token", token);
    }

    @GetMapping("/me")
    public User me(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtService.extractClaims(jwt).getSubject();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
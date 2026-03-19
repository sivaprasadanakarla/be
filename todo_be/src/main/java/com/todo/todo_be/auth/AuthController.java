package com.todo.todo_be.auth;

import com.todo.todo_be.auth.dto.LoginRequest;
import com.todo.todo_be.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // Placeholder auth flow for initial scaffold.
        if ("admin@todo.com".equalsIgnoreCase(request.email()) && "admin123".equals(request.password())) {
            return ResponseEntity.ok(new LoginResponse("dummy-jwt-token", "Login successful"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(null, "Invalid email or password"));
    }
}

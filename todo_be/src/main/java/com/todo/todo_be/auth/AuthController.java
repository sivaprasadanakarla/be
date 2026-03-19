package com.todo.todo_be.auth;

import com.todo.todo_be.auth.dto.ForgotPasswordRequest;
import com.todo.todo_be.auth.dto.ForgotPasswordResponse;
import com.todo.todo_be.auth.dto.LoginRequest;
import com.todo.todo_be.auth.dto.LoginResponse;
import com.todo.todo_be.auth.dto.RegisterRequest;
import com.todo.todo_be.auth.dto.RegisterResponse;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Dummy registration response – persistence to be wired in a future iteration.
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new RegisterResponse("Registration successful"));
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        // Dummy forgot-password response – email delivery to be wired in a future iteration.
        return ResponseEntity.ok(new ForgotPasswordResponse("If an account with that email exists, a password reset link has been sent"));
    }
}

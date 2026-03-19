package com.todo.todo_be.auth;

import com.todo.todo_be.auth.dto.ForgotPasswordRequest;
import com.todo.todo_be.auth.dto.ForgotPasswordResponse;
import com.todo.todo_be.auth.dto.LoginRequest;
import com.todo.todo_be.auth.dto.LoginResponse;
import com.todo.todo_be.auth.dto.RegisterRequest;
import com.todo.todo_be.auth.dto.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthControllerTest {

    private final AuthController authController = new AuthController();

    @Test
    void loginShouldReturnTokenForValidCredentials() {
        LoginRequest request = new LoginRequest("admin@todo.com", "admin123");

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("dummy-jwt-token", response.getBody().token());
        assertEquals("Login successful", response.getBody().message());
    }

    @Test
    void loginShouldReturnUnauthorizedForInvalidCredentials() {
        LoginRequest request = new LoginRequest("user@todo.com", "wrong");

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().token());
        assertEquals("Invalid email or password", response.getBody().message());
    }

    @Test
    void registerShouldReturnCreatedForValidRequest() {
        RegisterRequest request = new RegisterRequest("Alice", "alice@example.com", "Secret1@pass");

        ResponseEntity<RegisterResponse> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Registration successful", response.getBody().message());
    }

    @Test
    void forgotPasswordShouldReturnOkForValidEmail() {
        ForgotPasswordRequest request = new ForgotPasswordRequest("alice@example.com");

        ResponseEntity<ForgotPasswordResponse> response = authController.forgotPassword(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(
            "If an account with that email exists, a password reset link has been sent",
            response.getBody().message()
        );
    }
}

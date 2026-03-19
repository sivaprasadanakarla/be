package com.todo.todo_be.auth.dto;

public record LoginResponse(
    String token,
    String message
) {
}

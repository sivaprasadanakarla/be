package com.todo.todo_be.todo.dto;

public record UpdateTodoRequest(
    String title,

    String description,

    Boolean completed
) {
}

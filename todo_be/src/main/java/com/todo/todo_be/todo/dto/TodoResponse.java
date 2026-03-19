package com.todo.todo_be.todo.dto;

import com.todo.todo_be.todo.Todo;

public record TodoResponse(
    long id,
    String title,
    String description,
    boolean completed
) {
    public static TodoResponse from(Todo todo) {
        return new TodoResponse(todo.id(), todo.title(), todo.description(), todo.completed());
    }
}

package com.todo.todo_be.todo;

public record Todo(
    long id,
    String title,
    String description,
    boolean completed
) {
}

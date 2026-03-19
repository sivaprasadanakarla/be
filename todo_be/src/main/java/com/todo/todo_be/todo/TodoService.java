package com.todo.todo_be.todo;

import com.todo.todo_be.todo.dto.CreateTodoRequest;
import com.todo.todo_be.todo.dto.TodoResponse;
import com.todo.todo_be.todo.dto.UpdateTodoRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoResponse create(CreateTodoRequest request) {
        Todo todo = repository.save(request.title(), request.description());
        return TodoResponse.from(todo);
    }

    public List<TodoResponse> findAll() {
        return repository.findAll().stream()
            .map(TodoResponse::from)
            .toList();
    }

    public Optional<TodoResponse> findById(long id) {
        return repository.findById(id).map(TodoResponse::from);
    }

    public Optional<TodoResponse> update(long id, UpdateTodoRequest request) {
        return repository.findById(id).map(existing -> {
            Todo updated = new Todo(
                existing.id(),
                request.title() != null ? request.title() : existing.title(),
                request.description() != null ? request.description() : existing.description(),
                request.completed() != null ? request.completed() : existing.completed()
            );
            return repository.update(id, updated).map(TodoResponse::from);
        }).flatMap(opt -> opt);
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }
}

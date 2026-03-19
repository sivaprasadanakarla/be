package com.todo.todo_be.todo;

import com.todo.todo_be.todo.dto.CreateTodoRequest;
import com.todo.todo_be.todo.dto.TodoResponse;
import com.todo.todo_be.todo.dto.UpdateTodoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TodoControllerTest {

    private TodoController controller;

    @BeforeEach
    void setUp() {
        controller = new TodoController(new TodoService(new TodoRepository()));
    }

    @Test
    void createShouldReturnCreatedTodo() {
        CreateTodoRequest request = new CreateTodoRequest("Buy groceries", "Milk and eggs");

        ResponseEntity<TodoResponse> response = controller.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Buy groceries", response.getBody().title());
        assertEquals("Milk and eggs", response.getBody().description());
        assertFalse(response.getBody().completed());
    }

    @Test
    void findAllShouldReturnAllTodos() {
        controller.create(new CreateTodoRequest("Task 1", null));
        controller.create(new CreateTodoRequest("Task 2", "Details"));

        ResponseEntity<List<TodoResponse>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void findByIdShouldReturnTodoWhenExists() {
        ResponseEntity<TodoResponse> created = controller.create(new CreateTodoRequest("Read book", null));
        long id = created.getBody().id();

        ResponseEntity<TodoResponse> response = controller.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Read book", response.getBody().title());
    }

    @Test
    void findByIdShouldReturn404WhenNotFound() {
        ResponseEntity<TodoResponse> response = controller.findById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateShouldModifyTodoByKey() {
        ResponseEntity<TodoResponse> created = controller.create(new CreateTodoRequest("Old title", "Old desc"));
        long id = created.getBody().id();

        UpdateTodoRequest updateRequest = new UpdateTodoRequest("New title", "New desc", true);
        ResponseEntity<TodoResponse> response = controller.update(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals("New title", response.getBody().title());
        assertEquals("New desc", response.getBody().description());
        assertEquals(true, response.getBody().completed());
    }

    @Test
    void updateShouldPreserveExistingFieldsWhenNotProvided() {
        ResponseEntity<TodoResponse> created = controller.create(new CreateTodoRequest("My task", "My desc"));
        long id = created.getBody().id();

        UpdateTodoRequest updateRequest = new UpdateTodoRequest("Updated title", null, null);
        ResponseEntity<TodoResponse> response = controller.update(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated title", response.getBody().title());
        assertEquals("My desc", response.getBody().description());
        assertFalse(response.getBody().completed());
    }

    @Test
    void updateShouldPreserveTitleWhenNotProvided() {
        ResponseEntity<TodoResponse> created = controller.create(new CreateTodoRequest("Keep this title", "Old desc"));
        long id = created.getBody().id();

        UpdateTodoRequest updateRequest = new UpdateTodoRequest(null, "New desc", true);
        ResponseEntity<TodoResponse> response = controller.update(id, updateRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Keep this title", response.getBody().title());
        assertEquals("New desc", response.getBody().description());
        assertEquals(true, response.getBody().completed());
    }

    @Test
    void updateShouldReturn404WhenTodoNotFound() {
        UpdateTodoRequest updateRequest = new UpdateTodoRequest("New title", null, null);

        ResponseEntity<TodoResponse> response = controller.update(999L, updateRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteShouldReturn204WhenTodoExists() {
        ResponseEntity<TodoResponse> created = controller.create(new CreateTodoRequest("To delete", null));
        long id = created.getBody().id();

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteShouldReturn404WhenTodoNotFound() {
        ResponseEntity<Void> response = controller.delete(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

package com.todo.todo_be.todo;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    private final ConcurrentHashMap<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public Todo save(String title, String description) {
        long id = idSequence.getAndIncrement();
        Todo todo = new Todo(id, title, description, false);
        store.put(id, todo);
        return todo;
    }

    public Optional<Todo> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Todo> findAll() {
        return store.values();
    }

    public Optional<Todo> update(long id, Todo updated) {
        if (!store.containsKey(id)) {
            return Optional.empty();
        }
        store.put(id, updated);
        return Optional.of(updated);
    }

    public boolean deleteById(long id) {
        return store.remove(id) != null;
    }
}

package fr.wcs.sylene.yourtodolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import fr.wcs.sylene.yourtodolist.model.Todo;
import fr.wcs.sylene.yourtodolist.repository.TodoRepository;

@Controller
public class TodoAPIController {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/api/todos")
    @ResponseBody
    public List<Todo> getAllTodos() {

        return todoRepository.findAll();
    }

    @PostMapping("/api/todo")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Todo postTodo(@RequestBody Todo todo) {
        if (todo.getContent() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing content");
        if (todo.getTodoDate() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing date");

        return todoRepository.save(todo);
    }

    @PutMapping("/api/todo")
    @ResponseBody
    public Todo update(@RequestBody Todo todo) {
        if (todo.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing id");
        else
            return todoRepository.save(todo);
    }

    @GetMapping("/api/todo/{id}")
    @ResponseBody
    public Todo getOneTodo(@PathVariable Long id) {

        return todoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "could not find todo with id> " + id));

    }

    @GetMapping("/api/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        if (id != null) {
            todoRepository.deleteById(id);
            return null;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not delete team with uuid> " + id);
        }

    }

}
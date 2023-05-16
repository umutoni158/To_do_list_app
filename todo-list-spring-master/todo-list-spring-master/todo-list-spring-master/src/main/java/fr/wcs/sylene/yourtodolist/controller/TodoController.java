package fr.wcs.sylene.yourtodolist.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import fr.wcs.sylene.yourtodolist.model.Todo;
import fr.wcs.sylene.yourtodolist.repository.TodoRepository;

@Controller
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/todo")
    public String getTodoPage(Model model) {

        Todo todo = new Todo();
        model.addAttribute("todo", todo);

        return "todo";
    }

    @GetMapping("/todos")
    public String getAllTodos(Model model) {

        model.addAttribute("todos", todoRepository.findAll());
        return "todos";
    }

    @PostMapping("/todo")
    public String postTodo(@ModelAttribute Todo todo) {

        if (todo.getContent() == null || todo.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.html");
        }

        todoRepository.save(todo);

        return "redirect:/todos";
    }

    @GetMapping("/todo/{id}")
    public String getOneTodoToUpdate(Model model, @PathVariable Long id) {

        Todo todo = new Todo();

        if (id != null) {
            Optional<Todo> optionalTodo = todoRepository.findById(id);
            if (optionalTodo.isPresent()) {
                todo = optionalTodo.get();
            }
        }

        model.addAttribute("todo", todo);

        return "todo";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {

        todoRepository.deleteById(id);

        return "redirect:/todos";
    }

}
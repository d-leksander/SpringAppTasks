package io.Daro.project.controller;

import io.Daro.project.model.Task;
//import io.Daro.project.model.TaskRepository;
import io.Daro.project.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
class TaskController {

    private final TaskRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the task");
        return  ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return  ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id){
        Optional<Task> res = repository.findById(id);
        /*if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }*/
        return res.map(task -> ResponseEntity.ok(task)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task task){
        //return repository.save(task);
        Task created = repository.save(task);
        return ResponseEntity.created(URI.create(String.valueOf("/" + created.getId()))).body(created);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id,  @RequestBody @Valid Task task) {
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
    }
        task.setId(id);
        repository.save(task);
        return ResponseEntity.noContent().build();
    }
}

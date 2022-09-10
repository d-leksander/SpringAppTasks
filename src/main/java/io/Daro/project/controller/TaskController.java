package io.Daro.project.controller;

import io.Daro.project.model.Task;
import io.Daro.project.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RepositoryRestController
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
}

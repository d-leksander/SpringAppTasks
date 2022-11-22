package io.Daro.project.controller;

import io.Daro.project.model.Task;
//import io.Daro.project.model.TaskRepository;
import io.Daro.project.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/tasks")
class TaskController {

    private final TaskRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    //@Autowired
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }
    //@RequestMapping(method = RequestMethod.GET)

    @GetMapping(params = {"!sort", "!page", "!size"})
    //@ResponseBody

    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the task");
        //return services.findAllAsync().thenApply(ResponseEntity::ok);
        return  ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    //@ResponseBody
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return  ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/{id}")
    //@ResponseBody
    ResponseEntity<Task> readTask(@PathVariable int id){
        Optional<Task> res = repository.findById(id);
        /*if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }*/
        return res.map(task -> ResponseEntity.ok(task)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(repository.findByDone(state));
    }

    @PostMapping
    //@ResponseBody
    ResponseEntity<Task> createTask(@RequestBody @Valid Task task){
        //return repository.save(task);
        Task created = repository.save(task);
        return ResponseEntity.created(URI.create(String.valueOf("/" + created.getId()))).body(created);
    }
    @PutMapping(value = "/{id}")
    //@ResponseBody
    ResponseEntity<?> updateTask(@PathVariable int id,  @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task->{
            task.updateFrom(toUpdate);
            repository.save(task);
        });

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    //@ResponseBody
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
    }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}

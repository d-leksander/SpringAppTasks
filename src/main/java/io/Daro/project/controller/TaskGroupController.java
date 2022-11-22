package io.Daro.project.controller;

import io.Daro.project.logic.TaskGroupService;
import io.Daro.project.model.Task;
import io.Daro.project.model.TaskRepository;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskRepository groupRepository;
    private final TaskGroupService groupService;
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);

    TaskGroupController(final TaskRepository groupRepository, final TaskGroupService groupService) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }


    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel create) {
        GroupReadModel result = groupService.createGroup(create);
        //return ResponseEntity.created(URI.create(String.valueOf("/" + created.getId()))).body(created);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(groupService.createGroup(create));
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        //logger.warn("Exposing all the group");
        return ResponseEntity.ok(groupService.readAll());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(groupRepository.findAllByGroup_Id(id));

    }

    @Transactional
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        groupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
}
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}

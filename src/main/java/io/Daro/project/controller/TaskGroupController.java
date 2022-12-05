package io.Daro.project.controller;

import io.Daro.project.logic.TaskGroupService;
import io.Daro.project.model.*;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupTaskWriteModel;
import io.Daro.project.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Controller
@IllegalExceptionProcessing
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskRepository groupRepository;
    private final TaskGroupService groupService;
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);

    TaskGroupController(final TaskRepository groupRepository, final TaskGroupService groupService) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        //var projectToEdit = new ProjectWriteModel();
        //projectToEdit.setDescription("test");
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        groupService.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", toGroups());
        model.addAttribute("message", "Dodano grupę!");
        return "groups";
    }

    @PostMapping(params = "addTask", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }



    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel create) {
        GroupReadModel result = groupService.createGroup(create);
        //return ResponseEntity.created(URI.create(String.valueOf("/" + created.getId()))).body(created);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(groupService.createGroup(create));
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        //logger.warn("Exposing all the group");
        return ResponseEntity.ok(groupService.readAll());
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(groupRepository.findAllByGroup_Id(id));

    }

    @ResponseBody
    @Transactional
    @PatchMapping(value = "/{id}")

    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        groupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("groups")//
    List<GroupReadModel> toGroups() {
        return groupService.readAll();
    }



}

package io.Daro.project.controller;

import io.Daro.project.logic.ProjectService;
import io.Daro.project.model.Project;
import io.Daro.project.model.ProjectSteps;
import io.Daro.project.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
class ProjectController {

    private final ProjectService service;

    ProjectController(final ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProject(Model model, Authentication auth) {
        //var projectToEdit = new ProjectWriteModel();
        //projectToEdit.setDescription("test");
        if (auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_ADMIN"))) {
            model.addAttribute("project", new ProjectWriteModel());
            return "projects";
        }
        return "index";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "projects";
        }
        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProjects());
        model.addAttribute("message", "Dodano projekt!");
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }

    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            service.createGroup(deadline, id);
            model.addAttribute("message", "Dodano grupę!");
            //model.addAttribute("message", "Dodano grupę!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Błąd podczas tworzenia grupy!");
        }
        return "projects";
    }
    @ModelAttribute("projects")//
    List<Project> getProjects() {
        return service.readAll();
    }

}

package io.Daro.project.controller;

import io.Daro.project.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
class ProjectController {
    @GetMapping
    String showProject(Model model){
        //var projectToEdit = new ProjectWriteModel();
        //projectToEdit.setDescription("test");
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }

}

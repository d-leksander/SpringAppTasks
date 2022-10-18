package io.Daro.project.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name="projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "The value must be not empty!!!")
    private String description;


    @OneToMany(mappedBy = "project")
    private Set<ProjectSteps> projectSteps;


    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> taskGroups;

    int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

    Set<ProjectSteps> getProjectSteps() {
        return projectSteps;
    }

    void setProjectSteps(final Set<ProjectSteps> projectSteps) {
        this.projectSteps = projectSteps;
    }

    Set<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    void setTaskGroups(final Set<TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }
}

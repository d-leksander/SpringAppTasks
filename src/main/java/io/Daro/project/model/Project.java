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


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectSteps> projectSteps;


    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> taskGroups;

    public int getId() {
        return id;
    }

    void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

//nie zawsze jest to bezpieczne gdy mamy public
    public Set<ProjectSteps> getProjectSteps() {
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

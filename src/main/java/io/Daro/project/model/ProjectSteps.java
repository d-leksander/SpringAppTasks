package io.Daro.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "project_steps")
class ProjectSteps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "The value must be not empty!!!")
    private String description;
    private Long days_to_deadline;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

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

    Long getDays_to_deadline() {
        return days_to_deadline;
    }

    void setDays_to_deadline(final Long days_to_deadline) {
        this.days_to_deadline = days_to_deadline;
    }
}


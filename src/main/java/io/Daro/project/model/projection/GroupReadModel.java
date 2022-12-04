package io.Daro.project.model.projection;

import io.Daro.project.model.Task;
import io.Daro.project.model.TaskGroup;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private int id;
    private String description;
    /**deadline from the latest task group*/
    private LocalDateTime deadline;

    @NotBlank(message = "Task group description must be not empty!!!")
    private List<GroupTaskReadModel> tasks;


    public GroupReadModel(TaskGroup source){
        id = source.getId();
        description= source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date->deadline=date);
        tasks = source.getTasks().stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toList());
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(final List<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }


}

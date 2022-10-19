package io.Daro.project.model.projection;

import io.Daro.project.model.Task;

import java.time.LocalDateTime;

class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

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

    private Task toTask() {
        return new Task(description,deadline);
    }
}
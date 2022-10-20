package io.Daro.project.model.projection;

import io.Daro.project.model.TaskGroup;
import org.apache.catalina.Group;

import java.util.Set;
import java.util.stream.Collectors;

class GroupWriteModel {

    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;

    }

    public TaskGroup toGroup(){
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(tasks.stream()
                .map(GroupTaskWriteModel::toTask)
                .collect(Collectors.toSet()));
        return result;
    }
}

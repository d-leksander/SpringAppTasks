package io.Daro.project.reports;

import io.Daro.project.model.Task;
import io.Daro.project.model.event.TaskEvent;

import java.util.Objects;

public class DoneTask {
    String text;
    boolean doTaskBeforeDeadline;


    DoneTask(PersistedTaskEvent event, TaskEvent taskEvent, Task task) {
        text = event.name;
        //final LocalDateTime deadline = task.getDeadline();
        doTaskBeforeDeadline = task.getDeadline() == null || event.occurrence.isBefore(task.getDeadline());
    }

    public boolean doneBeforeDeadline() {
        return doTaskBeforeDeadline;
    }

    public String taskTxt() {
        return text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DoneTask doneTask = (DoneTask) o;
        return doTaskBeforeDeadline == doneTask.doTaskBeforeDeadline && Objects.equals(text, doneTask.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, doTaskBeforeDeadline);
    }
}

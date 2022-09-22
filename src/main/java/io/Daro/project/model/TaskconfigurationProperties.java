package io.Daro.project.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("task")
class TaskconfigurationProperties {
    private boolean allowMultipleTasksFromTemplate;

    boolean isAllowMultipleTasksFromTemplate() {
        return allowMultipleTasksFromTemplate;
    }

    void setAllowMultipleTasksFromTemplate(final boolean allowMultipleTasksFromTemplate) {
        this.allowMultipleTasksFromTemplate = allowMultipleTasksFromTemplate;
    }
}

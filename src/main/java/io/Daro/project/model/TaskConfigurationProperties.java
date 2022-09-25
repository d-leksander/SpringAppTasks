package io.Daro.project.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix= "task")
 public class TaskConfigurationProperties {
    private AllowMultipleTasks template;

     public AllowMultipleTasks getTemplate() {
        return template;
    }

    public void setTemplate(final AllowMultipleTasks template) {
        this.template = template;
    }

    public static class AllowMultipleTasks {
        boolean allowMultipleTasks;

         public boolean isAllowMultipleTasks() {
            return allowMultipleTasks;
        }

        public void setAllowMultipleTasks(final boolean allowMultipleTasks) {
            this.allowMultipleTasks = allowMultipleTasks;
        }
    }

}

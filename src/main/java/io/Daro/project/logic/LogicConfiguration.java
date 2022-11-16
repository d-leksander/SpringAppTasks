package io.Daro.project.logic;


import io.Daro.project.model.ProjectRepository;
import io.Daro.project.model.TaskConfigurationProperties;
import io.Daro.project.model.TaskGroupRepository;
import io.Daro.project.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService taskGroupService,
            final TaskConfigurationProperties configurationProperties
    ){
        return new ProjectService(repository, taskGroupRepository, taskGroupService, configurationProperties);
    }

    @Bean
    TaskGroupService taskGroupService(final TaskGroupRepository repository,
                                      final TaskRepository taskRepository
    ){
        return new TaskGroupService(repository, taskRepository);
    }
}

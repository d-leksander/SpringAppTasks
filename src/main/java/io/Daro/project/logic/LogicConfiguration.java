package io.Daro.project.logic;


import io.Daro.project.model.ProjectRepository;
import io.Daro.project.model.TaskConfigurationProperties;
import io.Daro.project.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService service(final ProjectRepository repository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties configurationProperties){
        return new ProjectService(repository, taskGroupRepository, configurationProperties);
    }
}

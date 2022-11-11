package io.Daro.project.logic;

import io.Daro.project.model.*;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties configurationProperties;


    ProjectService(final ProjectRepository projectRepository, final TaskGroupRepository taskGroupRepository, final TaskConfigurationProperties configurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;

        this.configurationProperties = configurationProperties;
    }


    public List<Project> readAll() {
        return projectRepository.findAll();
    }
    public Project save(final Project isSave){
        projectRepository.save(isSave);
        return isSave;
    }


    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if(!configurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one unclosed group  in project is allowed");
        }
        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription((project.getDescription()));
                    targetGroup.setTasks(project.getProjectSteps().stream()
                        .map(projectSteps -> new Task(
                                        projectSteps.getDescription(), deadline.plusDays(projectSteps.getDays_to_deadline()))
                        ).collect(Collectors.toSet())
                        );
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
                return new GroupReadModel(result);
    }


}

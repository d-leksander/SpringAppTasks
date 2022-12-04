package io.Daro.project.logic;

import io.Daro.project.model.*;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupTaskWriteModel;
import io.Daro.project.model.projection.GroupWriteModel;
import io.Daro.project.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService taskGroupService;
    private TaskConfigurationProperties configurationProperties;


    ProjectService(final ProjectRepository projectRepository, final TaskGroupRepository taskGroupRepository, final TaskGroupService taskGroupService, final TaskConfigurationProperties configurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.configurationProperties = configurationProperties;
    }


    public List<Project> readAll() {
        return projectRepository.findAll();
    }
    public Project save(final ProjectWriteModel isSave){
        return projectRepository.save(isSave.toProject());
        //return isSave;
    }


    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if(!configurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one unclosed group  in project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    //service.createGroup()
                    targetGroup.setTasks(
                            project.getProjectSteps().stream()
                                    .map(projectSteps -> {
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(projectSteps.getDescription());
                                        task.setDeadline(deadline.plusDays(projectSteps.getDays_to_deadline()));
                                        return task;

                                                //projectSteps.getDescription(), deadline.plusDays(projectSteps.getDays_to_deadline()))
                                    }
                        ).collect(Collectors.toList())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                    //targetGroup.setProject(project);

                    //return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }


}

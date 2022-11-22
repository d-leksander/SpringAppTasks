package io.Daro.project.logic;

import io.Daro.project.model.*;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Service
public class TaskGroupService {

    private TaskGroupRepository repository;
    //private TaskConfigurationProperties configurationProperties;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        //this.configurationProperties = configurationProperties;
        this.taskRepository = taskRepository;
    }
    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source,null);
    }
    GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id((groupId))){
            throw new IllegalStateException("Group has not finished tasks. Must be closed all the tasks");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Task group with given is not found"));
        result.setDone((!result.isDone()));
        repository.save(result);
    }


}

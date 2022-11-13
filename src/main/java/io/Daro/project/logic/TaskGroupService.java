package io.Daro.project.logic;

import io.Daro.project.model.TaskConfigurationProperties;
import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import io.Daro.project.model.TaskRepository;
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
        TaskGroup result = repository.save(source.toGroup());
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

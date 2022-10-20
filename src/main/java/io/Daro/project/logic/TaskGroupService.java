package io.Daro.project.logic;

import io.Daro.project.model.Task;
import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import io.Daro.project.model.projection.GroupReadModel;
import io.Daro.project.model.projection.GroupWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {

    private TaskGroupRepository repository;

    TaskGroupService(final TaskGroupRepository repository) {
        this.repository = repository;
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
}

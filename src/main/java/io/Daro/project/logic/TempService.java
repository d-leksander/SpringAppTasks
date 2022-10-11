package io.Daro.project.logic;

import io.Daro.project.model.Task;
import io.Daro.project.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

class TempService {


    @Autowired
    List<String> temp (TaskGroupRepository taskGroupRepository){


        //FIXME: n+1 selectow
        return taskGroupRepository.findAll().stream().flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription).collect(Collectors.toList());
    }
}

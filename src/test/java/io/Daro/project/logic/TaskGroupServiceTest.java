package io.Daro.project.logic;

import io.Daro.project.model.ProjectRepository;
import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import io.Daro.project.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.catchThrowable;

class TaskGroupServiceTest {



    @Test
    @DisplayName("test1")
    void existsByDoneIsFalseAndGroup_isTrue_illegal_State_Exception(){
        //TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        var ToDoTest = new TaskGroupService(null, mockTaskRepository);
        var exc = catchThrowable(()-> ToDoTest.toggleGroup(1));

        assertThat(exc)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has not finished tasks");
    }

    @Test
    @DisplayName("test2")
    void find_ById_illegal_Argument_Exception(){
        var mockRep = mock(TaskGroupRepository.class);
        when(mockRep.findById(anyInt())).thenReturn(Optional.empty());

        //TaskRepository mockTaskRepository = taskRepositoryReturning((true));
        //TaskGroupRepository mockTaskGroupRepository = groupRepositoryReturning(1);
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        var toDoTest = new TaskGroupService(mockRep, mockTaskRepository);
        //when
        var exc = catchThrowable(() -> toDoTest.toggleGroup(0));
        //then
        assertThat(exc)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is not found");
    }

    @Test
    @DisplayName("test done")
    void done_toggle_Group(){

        var taskRep = taskRepositoryReturning(false);
        var taskGroup = new TaskGroup();
        var taskGroupRep = groupRepositoryReturning(Optional.of(taskGroup));
        //var isDone = false;
        taskGroup.setDone(false);

        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(Optional.of(taskGroup));

        var toDoTest = new TaskGroupService(mockGroupRepository, mockTaskRepository);

        toDoTest.toggleGroup(taskGroup.getId());
        assertThat(taskGroup.isDone()).isTrue();

    }



    private static TaskRepository taskRepositoryReturning (final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        //when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
    private static TaskGroupRepository groupRepositoryReturning(Optional<TaskGroup> taskGroup) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.findById(anyInt())).thenReturn(taskGroup);
        return mockGroupRepository;
    }


}

package io.Daro.project.logic;

import io.Daro.project.model.ProjectRepository;
import io.Daro.project.model.TaskConfigurationProperties;
import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exist")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupsExist_throwsIllegalStateException() {
        //given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        //assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(1));
        TaskConfigurationProperties mockConfig = ConfigurationReturning(false);
        //system under tests
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Only one unclosed group");

    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configuration is ok and no project for a given id")
    void createGroup_ConfigurationOk_And_noProject_throwsIllegalArgumentException() {
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
        //system under tests
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");

    }

    @Test
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and no project for a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupsExist_And_noProject_throwsIllegalArgumentException() {
        //given
        groupRepositoryReturning(false);
        //and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
        //system under tests
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        //when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");

    }

    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_createsAndSavesGroup(){
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        //and
        TaskConfigurationProperties mockConfig = ConfigurationReturning(true);
    }




    private static TaskGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private static TaskConfigurationProperties ConfigurationReturning(final boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.AllowMultipleTasks.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        //and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private TaskGroupRepository inMemoryGroupRepository() {
        return new TaskGroupRepository(){
            private Map<Integer, TaskGroup> map = new HashMap<>();
            private int index = 0;
            @Override
            public List<TaskGroup> findAll() {
                return new ArrayList<>(map.values());
            }

            @Override
            public Optional<TaskGroup> findById(final Integer id) {
                return Optional.ofNullable(map.get(id));
            }

            @Override
            public TaskGroup save(final TaskGroup entity) {
                if(entity.getId()==0){
                    try{
                        TaskGroup.class.getDeclaredField("id").set(entity, ++index);
                    } catch (NoSuchFieldException | IllegalAccessException e){
                        throw new RuntimeException(e);
                    }
                }
                map.put(entity.getId(), entity);
                return entity;
            }

            @Override
            public boolean existsByDoneIsFalseAndProject_Id(final Integer projectId) {
                return map.values().stream()
                        .filter(group ->!group.isDone())
                        .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
            }
        };
    }
}
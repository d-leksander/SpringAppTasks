package io.Daro.project.logic;

import io.Daro.project.model.TaskConfigurationProperties;
import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw IllegalSteteException when configured to allow just 1 group and the other undonegroup exist")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupsExist_throwsIllegalSteteException() {
        //given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //and
        //assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(1));
        var mockTemplate = mock(TaskConfigurationProperties.AllowMultipleTasks.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        //and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        //system under tests
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

        //when+ then
        //toTest.createGroup(0, LocalDateTime.now());
        assertThatThrownBy(() -> {
            toTest.createGroup(0, LocalDateTime.now());
        }).isInstanceOf(IllegalStateException.class);

        //then


    }
}
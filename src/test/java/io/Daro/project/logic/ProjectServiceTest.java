package io.Daro.project.logic;

import io.Daro.project.model.TaskGroup;
import io.Daro.project.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
        //assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(1));
        //when
        //then


    }
}
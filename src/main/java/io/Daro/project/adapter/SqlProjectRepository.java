package io.Daro.project.adapter;

import io.Daro.project.model.Project;
import io.Daro.project.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer>{


        @Override
        @Query("select project from Project project join fetch project.projectSteps")
        List<Project> findAll();
    }


package io.Daro.project.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {

    //@RestResource(path = "done", rel = "done")
    //List<Task> findByDone(@Param("state") boolean done);
}

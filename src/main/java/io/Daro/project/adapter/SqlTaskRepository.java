package io.Daro.project.adapter;

import io.Daro.project.model.Task;
import io.Daro.project.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {

    //@Override
    //@Query(nativeQuery = true, value = "select count(*)>0 from tasks where id=")
    boolean existsById(@Param("id") Integer id);
    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    //@RestResource(path = "done", rel = "done")
    //List<Task> findByDone(@Param("state") boolean done);
}

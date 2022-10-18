package io.Daro.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
//@Inheritance(InheritanceType.TABLE_PER_CLASS)
@Table(name="task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task group description must be not empty!!!")
    private String description;
    private boolean done;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;




    public TaskGroup() {
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

     public void setDone(boolean done) {
        this.done = done;
    }

     public Set<Task> getTasks() {
        return tasks;
    }

    void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }

    /*public  void updateFrom(TaskGroup source){
        description = source.description;
        done = source.done;
    }*/
}

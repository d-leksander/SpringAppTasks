package io.Daro.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
//@Inheritance(InheritanceType.TABLE_PER_CLASS)
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "The value must be not empty!!!")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name= "task_group_id")
    private TaskGroup group;

    Task() {
    }

    public Task(String description, LocalDateTime deadline){
        this.description = description;
        this.deadline = deadline;
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

     public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

  /*  Audit getAudit() {
        return audit;
    }*/

    void setAudit(final Audit audit) {
        this.audit = audit;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(final TaskGroup group) {
        this.group = group;
    }

    public  void updateFrom(Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }
}

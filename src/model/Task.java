package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Comparable<Task>, Serializable {

    private String description;
    private int id;
    private String takenBy;

    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;

    Task(String description, TaskPrio prio, int id) {
        this.description = description;
        this.prio = prio;
        this.id = id;
        lastUpdate = LocalDate.now();
    }

    public void setState(TaskState state) {
        this.state = state;
        lastUpdate = LocalDate.now();
    }

    public void setPrio(TaskPrio prio) {
        this.prio = prio;
        lastUpdate = LocalDate.now();
    }

    public void setTakenBy(String takenBy) {
        if (takenBy == null) throw new IllegalStateException("Task already taken");
        this.takenBy = takenBy;
        lastUpdate = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getTakenBy() {
        return takenBy;
    }

    public TaskState getState() {
        return state;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public TaskPrio getPrio() {
        return prio;
    }

    @Override
    public int compareTo(Task other) {
        int result = this.prio.ordinal() - other.prio.ordinal();
        if (result == 0) {
            result = this.description.compareTo(other.getDescription());
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Task otherTask) {
            boolean r = this.prio == otherTask.getPrio();
            if (r) r = this.description.equals(otherTask.getDescription());
            return r;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", takenBy='" + takenBy + '\'' +
                ", state=" + state +
                ", lastUpdate=" + lastUpdate +
                ", prio=" + prio +
                '}';
    }
}

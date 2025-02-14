package model;

import model.ITaskMatcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Project implements Comparable<Project>, Serializable {

    ArrayList<Task> taskList;
    private String titel;
    private int id;
    private String description;
    private LocalDate created;
    private int nextTaskId = 0;

    Project(String titel, String descr, int id) {
        this.titel = titel;
        this.description = descr;
        this.id = id;
        created = LocalDate.now();

        taskList = new ArrayList<>();
    }

    public ArrayList<Task> findTasks(ITaskMatcher matcher) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task: taskList) {
            if (matcher.match(task)) {
                result.add(task);
            }
        }
        Collections.sort(result);
        return result;
    }

    public Task addTask(String descr, TaskPrio prio) {
        Task newTask = new Task(descr, prio, nextTaskId);
        taskList.add(newTask);
        nextTaskId++;
        return newTask;
    }

    public boolean removeTask(Task task) {
        boolean result = taskList.remove(task);
        nextTaskId--;
        return result;
    }

    ProjectState getState() {
        if(taskList.isEmpty()) return ProjectState.EMPTY;
        int nrOfDone = 0;
        for (Task task: taskList) {
            if(task.getState() == TaskState.DONE) nrOfDone++;
        }
        if (nrOfDone == taskList.size()) return ProjectState.COMPLETED;
        return ProjectState.ONGOING;
    }

    public LocalDate getLastUpdated(){
        if(taskList.isEmpty()) return created;
        LocalDate lastUpdated = taskList.get(0).getLastUpdate();
        for(Task task: taskList){
            if (task.getLastUpdate().isAfter(lastUpdated)) {
                lastUpdated = task.getLastUpdate();
            }
        }
        return lastUpdated;
    }

    public Task getTaskById(int id) {
        return taskList.get(id);
    }

    public String getTitel() {
        return titel;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public int getNextTaskId() {
        return nextTaskId;
    }

    @Override
    public int compareTo(Project other) {
        return this.titel.compareTo(other.getTitel());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Project otherProject) {
            return this.titel.equals(otherProject.getTitel());
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "%n+------------------+-------------------------+%n" +
                        "| %-16s | %-23s |%n" +
                        "+------------------+-------------------------+%n" +
                        "| %-16s | %-23s |%n" +
                        "| %-16s | %-23s |%n" +
                        "| %-16s | %-23s |%n" +
                        "| %-16s | %-23s |%n" +
                        "| %-16s | %-23d |%n" +
                        "+------------------+-------------------------+%n",
                "ID:", this.id,
                "Title:", this.titel,
                "Description:", this.description,
                "State:", getState(),
                "Created:", this.created,
                "Number of Tasks:", taskList.size()
        );
    }
}

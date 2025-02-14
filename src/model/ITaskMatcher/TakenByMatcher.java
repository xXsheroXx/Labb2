package model.ITaskMatcher;

import model.Task;

public class TakenByMatcher implements ITaskMatcher{

    private String takenBy;

    public TakenByMatcher(String takenBy) {
        this.takenBy = takenBy;
    }

    @Override
    public boolean match(Task task) {
        return this.takenBy.toLowerCase().contains(task.getTakenBy().toLowerCase());
    }
}

package model.ITaskMatcher;

import model.Task;
import model.TaskState;

public class NotDoneMatcher implements ITaskMatcher{


    @Override
    public boolean match(Task task) {
        return task.getState() != TaskState.DONE;
    }
}

package ui;

import model.ITaskMatcher.ITaskMatcher;
import model.ITaskMatcher.TakenByMatcher;
import model.ITaskMatcher.NotDoneMatcher;
import model.ITaskMatcher.PrioMatcher;
import model.*;

import java.util.List;
import java.util.Scanner;

/**
 * User interactions for a specific project, current project.
 * The user selects actions on current project in the projectLoop method.
 */
class CurrentProjectUI {
    private Project currentProject;
    private final Scanner scan;

    // package private visibility - only visible to other classes in
    // package ui - intended for MainUI.
    CurrentProjectUI(Scanner scan) {
        this.scan = scan;
        this.currentProject = null; // TODO: Ugly!
    }

    void setCurrentProject(Project project) {
        this.currentProject = project;
        projectLoop();
    }

    Project getCurrentProject() {
        return currentProject;
    }

    void projectLoop() {
        char choice;
        do {
            printCurrentProjectMenu();
            choice = InputUtils.scanAndReturnFirstChar(scan);

            switch (choice) {
                case 'T':
                    System.out.print("Name? ");
                    String takenBy = scan.nextLine();
                    findMatchBy(new TakenByMatcher(takenBy));
                    break;
                case 'N':
                    findMatchBy(new NotDoneMatcher());
                    break;
                case 'H':
                    findMatchBy(new PrioMatcher(TaskPrio.High));
                    break;
                case 'A':
                    addTask();
                    break;
                case 'U':
                    updateTask();
                    break;
                case 'X':
                    break;
                default:
                    System.out.println("Unknown command");
            }
        } while (choice != 'X');
    }

    private void findMatchBy(ITaskMatcher matcher) {
//        System.out.println("Project: " + currentProject.getTitle());
//        System.out.println(currentProject.toString());
        List<Task> tasks = currentProject.findTasks(matcher);
        printTasks(tasks);
    }

    private void addTask() {
        System.out.print("\nTask description:  ");
        String descr = scan.nextLine();
        System.out.print("Priority (L)Low, (M)Medium, (H)High? ");
        char prioChar = InputUtils.scanAndReturnFirstChar(scan);
        TaskPrio prio = prioChar == 'H' ? TaskPrio.High : prioChar == 'L' ? TaskPrio.Low : TaskPrio.Medium;
        currentProject.addTask(descr, prio);
    }

    private void updateTask() {
        System.out.print("Task id? ");
        int id = scan.nextInt();
        scan.nextLine(); //remove "new line" from scanner buffer
        Task task = currentProject.getTaskById(id);
        if (task != null) {
            System.out.println(task);
            System.out.print("New state (T)Todo (D)Done? ");
            char stateChar = InputUtils.scanAndReturnFirstChar(scan);
            if (stateChar == 'T') {
                System.out.print("Taken by: ");
                String name = scan.nextLine();
                task.setState(TaskState.TO_DO);
                task.setTakenBy(name);
            }
            else if(stateChar == ('D')) {
                task.setState(TaskState.DONE);
            }
        } else {
            System.out.println("Id not found.");
        }
    }

    private void printCurrentProjectMenu() {
        String menuFormat = "╔════════════   Manage  ════════════╗%n" +
                "║  T - List tasks taken by ...      ║%n" +
                "║  N - List tasks not done          ║%n" +
                "║  H - List high priority tasks     ║%n" +
                "║  A - Add task                     ║%n" +
                "║  U - Update task                  ║%n" +
                "║  X - Exit                         ║%n" +
                "╚═══════════════════════════════════╝%n" +
                "ENTER CHOICE => ";

        System.out.printf(menuFormat);
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (Task task : tasks) {
                System.out.println(task.toString());
            }
        }
    }
}

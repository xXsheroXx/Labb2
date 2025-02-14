import model.ProjectsManager;
import ui.MainUI;

public class Main {
    public static void main(String[] args) {
        ProjectsManager manager = new ProjectsManager();
        MainUI ui = new MainUI(manager);
    }
}
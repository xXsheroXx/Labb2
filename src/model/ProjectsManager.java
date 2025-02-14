package model;

import model.exceptions.TitleNotUniqueException;

import java.util.ArrayList;
import java.util.List;

public class ProjectsManager {

    private ArrayList<Project> projectList;
    private int nextProjectId = 0;

    public ProjectsManager() {
        projectList = new ArrayList<>();
    }

    public ArrayList<Project> getProjectList() {
        return new ArrayList<>(this.projectList);
    }

    public void setProjects(List<Project> incomingProjects) {
        projectList.clear();
        projectList.addAll(incomingProjects);
        nextProjectId = getHighestId();
    }

    public int getHighestId() {
        int count = 0;
        for (Object object: projectList) {
            if(object instanceof Project) {
                count++;
            }
        }
        return count;
    }

    public boolean isTitleUnique(String title) {
        for (Project project : projectList) {
            if (project.getTitel().equals(title)) return false;
        }
        return true;
    }



    public Project addProject(String title, String descr) {
        if (!isTitleUnique(title)) throw new TitleNotUniqueException();
        Project project = new Project(title, descr, nextProjectId);
        projectList.add(project);
        nextProjectId++;
        return project;
    }

    public void removeProject(Project project) {
        projectList.remove(project);
        nextProjectId--;
    }

    public Project getProjectById(int id) {
        for (Project project: projectList) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }

    public List<Project> findProjects(String titleStr) {
        ArrayList<Project> result = new ArrayList<>();
        for (Project project : projectList) {
            if (project.getTitel().contains(titleStr)) {
                result.add(project);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Project project: projectList) {
            str.append(project).append("\n");
        }
        return str.toString();
    }
}

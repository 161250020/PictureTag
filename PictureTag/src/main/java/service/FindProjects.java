package service;

import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public interface FindProjects {
    public Project lauchPro(Project pro);
    public ArrayList<Project> getProjects(String username);
    public Project getProject(String proid);
    public ArrayList<Task> getTasks(String ProId);
    public ArrayList<Project> chooseProjectByDate(String Date1, String Date2, String username);
    public void update(Project pro);
    public void updateTaskId(String proId,String taskId);
    public void updateProgress(String proid);
    public void updateList(String proid,String username,String taskId);
}


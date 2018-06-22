package stub;

import service.FindProjects;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public class FindProjectsstub implements FindProjects {
    public Project lauchPro(Project pro){
        return new Project();
    }
    public ArrayList<Project> getProjects(String username){
        return new ArrayList<Project>();
    }
    public Project getProject(String proid){
        return new Project();
    }
    public ArrayList<Task> getTasks(String ProId){
        return new ArrayList<Task>();
    }
    public ArrayList<Project> chooseProjectByDate(String Date1, String Date2, String username){
        return new ArrayList<Project>();
    }
    public void update(Project pro){

    }
    public void updateTaskId(String proId,String taskId){

    }
    public void updateProgress(String proid){

    }
    public void updateList(String proid,String username,String taskId){

    }
}

package stub;

import service.FindProjects;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public class FindProjectsstub implements FindProjects {
    public void lauchPro(Project pro){

    }
    /**
     *
     * @param
     * @param  :自己发布的项目   false:领取的项目
     * @return
     */
    public ArrayList<Project> getProjects(String username){return new ArrayList<Project>();}
    /**
     *
     * @param
     * @return
     */
    public Project getProject(String proid){
        return new Project();
    }
}

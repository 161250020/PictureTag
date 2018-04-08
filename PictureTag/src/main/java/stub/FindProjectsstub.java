package stub;

import service.FindProjects;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public class FindProjectsstub implements FindProjects {
    public ArrayList<projectInfo> getProjectInfo(){
        return new ArrayList<projectInfo>();
    }
    /**
     *
     * @param id 通过用户id来返回项目列表
     * @param b true:自己发布的项目   false:领取的项目
     * @return
     */
    public ArrayList<Project> getProjects(String id, boolean b){
        return new ArrayList<Project>();
    }
    /**
     *
     * @param id 通过project的id来返回task列表
     * @return
     */
    public ArrayList<Task> getTask(String id){
        return new ArrayList<Task>();
    }
}

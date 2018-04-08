package service;

import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public interface FindProjects {

    public ArrayList<projectInfo> getProjectInfo();


    /**
     *
     * @param id 通过用户id来返回项目列表
     * @param b true:自己发布的项目   false:领取的项目
     * @return
     */
    public ArrayList<Project> getProjects(String id, boolean b);

    /**
     *
     * @param id 通过project的id来返回task列表
     * @return
     */
    public ArrayList<Task> getTask(String id);

}

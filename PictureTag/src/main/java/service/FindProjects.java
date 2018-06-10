package service;

import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.util.ArrayList;

public interface FindProjects {

    public void lauchPro(Project pro);


    /**
     *
     * @param username 通过用户id来返回项目列表
     * @param :自己发布的项目   false:领取的项目
     * @return
     */
    public ArrayList<Project> getProjects(String username);

    //辅助方法
    public Project getProject(String proid);

}


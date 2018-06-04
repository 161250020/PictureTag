package service;

import vo.UserInfo;

import java.util.ArrayList;

public interface taskService {

    public String receiveTaskInfo(String taskId);

    public String getTaskId(String userId);//获得下一个taskId      等待修改

    public boolean acceptTask(String taskId,String userId);//接受任务

    public void newTask(String taskJson);//新建任务

    public String findTask(String taskId,String filePath);

    public boolean modifyTask(String taskData,String filePath);

    public void deleteTask(String taskId,String filepath);

    public boolean completeTask(String taskId,String userId);

}
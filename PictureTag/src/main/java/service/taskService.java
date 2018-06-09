package service;

import vo.UserInfo;

import java.util.ArrayList;

public interface taskService {

    public String receiveTaskInfo(String taskId);

    public String getTaskId(String userId);//获得下一个taskId

    public boolean acceptTask(String taskId,String userId);//接受任务

    public void newTask(String taskJson);//新建任务

    public void deleteTask(String taskId,String filepath);//删除task

    public boolean completeTask(String taskId,String userId);//完成task

    public String giveUpTask(String taskId,String userId);//放弃task

}

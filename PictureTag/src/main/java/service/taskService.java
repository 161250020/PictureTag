package service;

import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;

public interface taskService {

    public String receiveTaskInfo(String taskId);

    public String getTaskId(String userId);//获得下一个taskId

    public boolean acceptTask(String taskId, String userId);//接受任务

    public void newTask(String taskJson);//新建任务

    public boolean deleteTask(String taskId, String filepath);//删除task

    public boolean confirmTask(String taskId, String userId, double grade);//确认完成有效后系统修改数据

    public boolean completeTask(String taskId, String userId);//标注着确认完成task

    public void giveUpTask(String taskId, String userId);//放弃task

    public ArrayList<String> receiveTasks(String tagType);

    public void rePublishTask(String taskId);
}

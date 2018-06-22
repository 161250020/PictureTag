package Controller;

import service.taskService;
import serviceimpl.task.taskServiceImpl;

import java.util.ArrayList;

public class TaskController implements taskService {
    taskServiceImpl impl=new taskServiceImpl();
    public String receiveTaskInfo(String taskId){
        return impl.receiveTaskInfo(taskId);
    }

    public String getTaskId(String userId){
        return impl.getTaskId(userId);
    }

    public boolean acceptTask(String taskId, String userId){
        return impl.acceptTask(taskId,userId);
    }

    public void newTask(String taskJson){
         impl.newTask(taskJson);
    }

    public boolean deleteTask(String taskId, String filepath){
        return impl.deleteTask(taskId,filepath);
    }

    public boolean confirmTask(String taskId, String userId, double grade){
        return impl.confirmTask(taskId,userId,grade);
    }

    public boolean completeTask(String taskId, String userId){
        return impl.completeTask(taskId,userId);
    }

    public void giveUpTask(String taskId, String userId){
         impl.giveUpTask(taskId,userId);
    }

    public ArrayList<String> receiveTasks(String tagType){
        return impl.receiveTasks(tagType);
    }

    public void rePublishTask(String taskId){
        impl.rePublishTask(taskId);
    }
}

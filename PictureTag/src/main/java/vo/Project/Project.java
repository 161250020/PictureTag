package vo.Project;

import java.util.ArrayList;

public class Project {          //众包发起项目的信息,包含id,名称,需求和任务的id

    String id;//形如格式为userName+"^_^"+"00001"
    String name;
    int progress;//已经完成task的数量
    ArrayList<String> requests;
    ArrayList<String> taskIds;
    ArrayList<String> userIds;

    public Project(){}

    public Project(String id,String name,int progress){
        requests = new ArrayList<String>();
        taskIds = new ArrayList<String>();
        userIds = new ArrayList<String>();
        this.progress=progress;
        this.id = id;
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public ArrayList<String> getRequests() {

        return requests;
    }

    public void setRequests(ArrayList<String> requests) {

        this.requests = requests;
    }

    public ArrayList<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(ArrayList<String> taskIds) {
        this.taskIds = taskIds;
    }
}

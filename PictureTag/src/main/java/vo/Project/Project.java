package vo.Project;

import java.util.ArrayList;

public class Project {          //众包发起项目的信息,包含id,名称,需求和任务的id

    String id;
    String name;
    ArrayList<String> requests;
    ArrayList<String> taskIds;

    public Project(){}

    public Project(String id,String name){
        requests = new ArrayList<String>();
        taskIds = new ArrayList<String>();
        this.id = id;
        this.name = name;
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

package vo.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project {          //众包发起项目的信息,包含id,名称,需求和任务的id
    String username;            //发起者的用户名
    String id;                   //形如格式为userName+"^_^"+"00001"
    String name;
    int progress;//已经完成task的数量
    ArrayList<String> requests;
    ArrayList<String> taskIds;
    Map<String,String> list;   //领取者和对应的任务

    public Project(){
        this.username="";
        this.requests = new ArrayList<String>();
        this.taskIds = new ArrayList<String>();
        this.list= new HashMap<String,String>();
        this.progress=0;
        this.id = "";
        this.name = "";
    }

    public Project(String id,String name,int progress,String username){
        this.requests = new ArrayList<String>();
        this.taskIds = new ArrayList<String>();
        this.list= new HashMap<String,String>();
        this.progress=progress;
        this.id = id;
        this.name = name;
        this.username=username;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Map<String, String> getList() {
        return list;
    }

    public void setList(Map<String, String> list) {
        this.list = list;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

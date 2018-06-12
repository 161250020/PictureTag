package vo.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project {          //众包发起项目的信息,包含id,名称,需求和任务的id
    String username;            //发起者的用户名
    String id;                   //形如格式为userName+"^_^"+"00001"
    String name;
    String date;
    int progress;               //已经完成task的数量       完成task更新一次
    boolean finish;             //是否完成
    ArrayList<String> requests;
    ArrayList<String> taskIds;
    Map<String,String> list;    //领取者和对应的任务id,(注意:任务id应为key值,否则对于同一个)        领取的时候更新一下

    public Project(){
        this.username="";
        this.requests = new ArrayList<String>();
        this.taskIds = new ArrayList<String>();
        this.list= new HashMap<String,String>();
        this.progress=0;
        this.id = "";
        this.name = "";
        this.date="";
        this.finish=false;
    }

    public Project(String id,String name,int progress,String username){
        this.requests = new ArrayList<String>();
        this.taskIds = new ArrayList<String>();
        this.list= new HashMap<String,String>();
        this.progress=progress;
        this.id = id;
        this.name = name;
        this.username=username;
        this.date=date;
        this.finish=false;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean getFinish() {
        return finish;
    }
}

package vo.Project.Task;

import java.util.ArrayList;

public class Task {          //个体任务

    String id;
    String name;
    double socre;//完成task将获得的分数
    int progress;//已经完成图片的数量
    boolean flag;//判断task是否已经被接受
    ArrayList<String> imageIds;   //图片
    ArrayList<String> requests;   //请求


    public Task(){}

    public Task(String id,String name,double socre,int progress,boolean flag){
        imageIds = new ArrayList<String>();
        requests = new ArrayList<String>();
        this.id = id;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.flag = false;        //一开始任务是未被接受的,默认值是false
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ArrayList<String> getImageIds() { return imageIds; }

    public void setImageIds(ArrayList<String> imageIds) { this.imageIds = imageIds; }

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

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public double getProgress() {
        return progress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    //具体方法
    public void updateTask(Task newTask){
        //打开存储任务的文件夹,找到对应id的task,替换成新的

    }
    public void modifyflag(){
        this.flag=true;
    }
    public double getDgree(){
        double result=0.0;
        int total=imageIds.size();
        result=(1.0)*progress/total;
        return result;
    }
}

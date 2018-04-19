package vo.Project.Task;

import java.util.ArrayList;

public class Task {          //个体任务

    String id;
    String name;
    double socre;//完成task将获得的分数
    int progress;//已经完成图片的数量
    boolean flag;//判断task是否已经被接受
    ArrayList<String> imageIds;
    ArrayList<String> requests;


    public Task(){}

    public Task(String id,String name,double socre,int progress,boolean flag){
        imageIds = new ArrayList<String>();
        requests = new ArrayList<String>();
        this.id = id;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.flag = flag;
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
}

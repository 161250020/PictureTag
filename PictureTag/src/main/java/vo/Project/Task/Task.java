package vo.Project.Task;

import java.util.ArrayList;
import java.util.Date;

public class Task {          //个体任务

    String id;//形如格式为projectId+"^_^"+"00001"
    String name;
    double socre;//完成task将获得的分数
    int progress;//已经完成图片的数量
    boolean flag;//判断task是否已经被接受 true:接受
    boolean flag1;//判断task是否已经被发布 true:发布
    Date startDate;
    Date endDate;
    ArrayList<String> imageIds;
    ArrayList<String> requests;

    public Task(){
        this.flag = false;
        this.flag1 = false;
    }

    public Task(String id, String name, double socre, int progress, Date startDate, Date endDate){
        imageIds = new ArrayList<String>();
        requests = new ArrayList<String>();
        this.id = id;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.flag = false;
        this.flag1 = false;
        this.startDate = new Date();
        this.endDate = new Date();

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

    public boolean isFlag1() { return flag1; }

    public void setFlag1(boolean flag1) { this.flag1 = flag1; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

}

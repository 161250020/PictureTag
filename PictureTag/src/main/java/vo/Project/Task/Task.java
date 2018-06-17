package vo.Project.Task;

import java.util.ArrayList;
import java.util.Date;

public class Task {          //个体任务

    String id;//形如格式为projectId+"_"+"00001"
    String receiverId;//领取者的Id
    String name;
    String tagType;//标注类型
    double socre;//完成task将获得的分数
    int progress;//已经完成图片的数量
    boolean receive;//判断task是否已经被接受 true:接受
    boolean publish;//判断task是否已经被发布 true:发布
    boolean complete;//判断task是否已经被complete true:完成
    double grade;//评分

    String startDate;
    String endDate;
    ArrayList<String> imageIds;
    ArrayList<String> requests;

    public Task(){
        this.receive = false;
        this.publish = false;
        this.complete = false;
    }

    public Task(String id,String receiverId, String name, double socre, int progress, String startDate, String endDate){
        imageIds = new ArrayList<String>();
        requests = new ArrayList<String>();
        this.id = id;
        this.receiverId = receiverId;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.receive = false;
        this.publish = false;
        this.complete = false;
        this.startDate = startDate;
        this.endDate = endDate;

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

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isReceive() { return receive; }

    public void setReceive(boolean receive) { this.receive = receive; }

    public boolean isPublish() { return publish; }

    public void setPublish(boolean publish) { this.publish = publish; }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(ArrayList<String> imageIds) {
        this.imageIds = imageIds;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public double getGrade() { return grade; }

    public void setGrade(double grade) { this.grade = grade; }

    public boolean isComplete() { return complete; }

    public void setComplete(boolean complete) { this.complete = complete; }

    public String getReceiverId() { return receiverId; }

    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
}

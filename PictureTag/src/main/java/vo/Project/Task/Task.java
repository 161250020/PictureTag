package vo.Project.Task;

import java.util.ArrayList;
import java.util.Date;

public class Task {          //个体任务

    String id;//形如格式为projectId+"_"+"00001"
    String name;
    String tagType;//标注类型
    double socre;//完成task将获得的分数
    int progress;//已经完成图片的数量
    boolean flag;//判断task是否已经被接受 true:接受
    boolean flag1;//判断task是否已经被发布 true:发布
    String startDate;
    String endDate;
    ArrayList<String> imageIds;
    ArrayList<String> requests;

    public Task(){
        this.flag = false;
        this.flag1 = false;
    }

    public Task(String id, String name, double socre, int progress, String startDate, String endDate){
        imageIds = new ArrayList<String>();
        requests = new ArrayList<String>();
        this.id = id;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.flag = false;
        this.flag1 = false;
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

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
}

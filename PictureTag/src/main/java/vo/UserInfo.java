package vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//做一项任务,研究完成情况,图片张数,分数高低之间的关联规则
//可不可以设置一个追加积分的条款,就是达到一定用户评分后
public class UserInfo {
    public String username;
    public String password;
    public String name;
    public int level;
    public ArrayList<String> receivepro;       //对应的接受项目ID
    public ArrayList<String> launchpro;        //对应的发布项目
    public int TaskNumber;
    public double score;                      //用户的积分
    public Map<String,Double> receiveEvalu;     //用户的评价,key是任务id,value是用户评分
    public Map<String,Boolean> finish;           //用户对任务是否放弃,接受任务的同时要调用user的setMap方法

    public UserInfo(){
        this.username="";
        this.password="";
        this.name="";
        this.level=0;
        this.receivepro=new ArrayList<String>();
        this.launchpro=new ArrayList<String>();
        this.TaskNumber=0;
        this.score=0.0;
        this.receiveEvalu= new HashMap<String,Double>();
        this.finish=new HashMap<String,Boolean>();
    }
    public UserInfo(String username,String password){
        this.username=username;
        this.password=password;
        this.name="";
        this.level=0;
        this.receivepro=new ArrayList<String>();
        this.launchpro=new ArrayList<String>();
        this.TaskNumber=0;
        this.score=0.0;
        this.receiveEvalu= new HashMap<String,Double>();
        this.finish=new HashMap<String,Boolean>();
    }
    public UserInfo(String username,String password,String name,int level,ArrayList<String> receivepro,ArrayList<String> launchpro,int TaskNumber,double score,Map<String,Double> receiveEvalu,Map<String,Boolean> finish){
        this.username=username;
        this.password=password;
        this.name=name;
        this.level=level;
        this.receivepro=receivepro;
        this.launchpro=launchpro;
        this.TaskNumber=TaskNumber;
        this.score=score;
        this.receiveEvalu= receiveEvalu;
        this.finish=finish;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<String> getReceivepro() {
        return receivepro;
    }

    public void setReceivepro(ArrayList<String> receivepro) {
        this.receivepro = receivepro;
    }

    public ArrayList<String> getLaunchpro() {
        return launchpro;
    }

    public void setLaunchpro(ArrayList<String> launchpro) {
        this.launchpro = launchpro;
    }

    public int getTaskNumber() {
        return TaskNumber;
    }
    public void setTaskNumber(int taskNumber) {
        TaskNumber = taskNumber;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public void setReceiveEvalu(Map<String,Double> receiveEvalu){
        this.receiveEvalu=receiveEvalu;
    }
    public Map<String,Double> getReceiveEvalu(){
        return receiveEvalu;
    }
    public void setFinish(Map<String,Boolean> finish){
        this.finish=finish;
    }

    public Map<String, Boolean> getFinish() {
        return finish;
    }
}

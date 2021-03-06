package vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//做一项任务,研究完成情况,图片张数,分数高低之间的关联规则
public class UserInfo {
    public String username;
    public String password;
    public String nickname;
    public String name;
    public int level;
    public ArrayList<String> receivetask;        //对应的接受的任务ID
    public ArrayList<String> launchpro;         //对应的发布项目
    public int TaskNumber;                     //完成的任务数
    public double score;                        //用户的积分
    public Map<String,Double> receiveEvalu;     //用户的评价,key是任务id,value是用户评分
    public Map<String,Boolean> finish;           //用户对任务是否放弃,接受任务的同时要调用user的setMap方法
    public String Date;                          //用户注册的日期
    public String Month;                         //用户注册的月份
    public String Day;                           //用户注册的日子

    public UserInfo(){
        this.username="";
        this.password="";
        this.name="";
        this.level=0;
        this.receivetask=new ArrayList<String>();
        this.launchpro=new ArrayList<String>();
        this.TaskNumber=0;
        this.score=0.0;
        this.receiveEvalu= new HashMap<String,Double>();
        this.finish=new HashMap<String,Boolean>();
        this.nickname="";
        this.Date="";
        this.Month="";
        this.Day="";
    }
    public UserInfo(String username,String password){
        this.username=username;
        this.password=password;
        this.name="";
        this.level=0;
        this.receivetask=new ArrayList<String>();
        this.launchpro=new ArrayList<String>();
        this.TaskNumber=0;
        this.score=0.0;
        this.receiveEvalu= new HashMap<String,Double>();
        this.finish=new HashMap<String,Boolean>();
        this.nickname="";
        this.Date="";
        this.Month="";
        this.Day="";
    }
    public UserInfo(String username,String password,String name,int level,ArrayList<String> receivepro,ArrayList<String> launchpro,int TaskNumber,double score,Map<String,Double> receiveEvalu,Map<String,Boolean> finish,String nickname,String Date,String Month,String Day){
        this.username=username;
        this.password=password;
        this.name=name;
        this.level=level;
        this.receivetask=receivepro;
        this.launchpro=launchpro;
        this.TaskNumber=TaskNumber;
        this.score=score;
        this.receiveEvalu= receiveEvalu;
        this.finish=finish;
        this.nickname=nickname;
        this.Date=Date;
        this.Month=Month;
        this.Day=Day;
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

    public ArrayList<String> getReceivetask() {
        return receivetask;
    }

    public void setReceivetask(ArrayList<String> receivepro) {
        this.receivetask = receivepro;
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
    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDate() {
        return Date;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getDay() {
        return Day;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getMonth() {
        return Month;
    }
}

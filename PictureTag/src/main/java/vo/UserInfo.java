package vo;

import java.util.ArrayList;

public class UserInfo {
 public String username;
 public String password;
 public String name;
 public int level;
 public ArrayList<String> receivepro;   //对应的接受项目ID
 public ArrayList<String> launchpro;        //对应的发布项目
 public int TaskNumber;
 public double score;
 public UserInfo(String username,String password){
     this.username=username;
     this.password=password;
 }
 public UserInfo(String username,String password,String name,int level,ArrayList<String> receivepro,ArrayList<String> launchpro,int TaskNumber,double score){
     this.username=username;
     this.password=password;
     this.name=name;
     this.level=level;
     this.receivepro=receivepro;
     this.launchpro=launchpro;
     this.TaskNumber=TaskNumber;
     this.score=score;
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
}

package vo;

import vo.Project.Project;

public class UserInfo {
 public String username;
 public String password;
 public String name;
 public int level;
 public Project receivepro;   //对应的接受项目
 public Project launchpro;        //对应的发布项目
 public UserInfo(String username,String password){
     this.username=username;
     this.password=password;
 }
 public UserInfo(String username,String password,String name,int level,Project receivepro,Project launchpro){
     this.username=username;
     this.password=password;
     this.name=name;
     this.level=level;
     this.receivepro=receivepro;
     this.launchpro=launchpro;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Project getReceivepro() {
        return receivepro;
    }

    public void setReceivepro(Project receivepro) {
        this.receivepro = receivepro;
    }

    public Project getLaunchpro() {
        return launchpro;
    }

    public void setLaunchpro(Project launchpro) {
        this.launchpro = launchpro;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

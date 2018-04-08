package serviceimpl;

import service.user;
import util.FileReadandWrite;
import util.RevertJsonObject;
import vo.UserInfo;

import java.util.ArrayList;

public class userserviceImpl {
            public void start() {      //一开始有一个用户
                FileReadandWrite.WriteFile("PictureTag_Phase_Ⅰ/PictureTag/src/main/resources/TagFile/user.txt", "admin admin");
            }
            public ArrayList<UserInfo> getall(){
                ArrayList<String> content=FileReadandWrite.ReadFile("PictureTag_Phase_Ⅰ/PictureTag/src/main/resources/TagFile/user.txt");
                ArrayList<UserInfo> lis=new ArrayList<UserInfo>();
                for(int i=0;i<content.size();i++){
                    RevertJsonObject.getBean(content.get(i),UserInfo.class);
                }
                return lis;
            }
             public boolean login(String username,String password){
                      boolean flag=false;

                      return flag;
             }
             public boolean register(String username,String password){
                     boolean flag=false;
                     if(checksame(username)) {
                         flag = false;
                     }
                     else{
                          flag=true;
                          UserInfo user=new UserInfo(username,password);
                          FileReadandWrite.WriteFile("PictureTag_Phase_Ⅰ/PictureTag/src/main/resources/TagFile/user.txt",RevertJsonObject.toJsonObject(user));
                     }
                     return  flag;
             }
             public boolean delete(String username){
                     boolean flag=true;
                     if(!checksame(username)){
                         flag=false;
                     }
                     else{
                         ArrayList<UserInfo> lis=getall();
                         ArrayList<String> renew=new ArrayList<String>();
                         for(UserInfo user:lis){
                              if(!user.getUsername().equals(username)){
                                  renew.add(RevertJsonObject.toJsonObject(user));
                              }
                         }
                         for(String str:renew){
                               //这里有点疑问,到底该如何覆盖之前得,有清空这个方法吗
                         }
                     }
                     return   flag;
    }
             public boolean checksame(String username){
                     boolean flag=false;
                     ArrayList<UserInfo> lis=getall();
                     for(UserInfo user:lis){
                         if(user.getUsername().equals(username)){
                             return true;
                         }
                     }
                     return flag;
             }
             public boolean update(String username,String password){
                     boolean flag=false;
                     return false;
             }
             public UserInfo getUser(){
                     return new UserInfo("admin","admin");
             }
}

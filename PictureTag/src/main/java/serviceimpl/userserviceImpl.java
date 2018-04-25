package serviceimpl;

import com.google.gson.Gson;
import service.user;
import util.FileReadandWrite;
import util.RevertJsonObject;
import vo.UserInfo;

import java.io.File;
import java.util.ArrayList;

public class userserviceImpl {
            final String path="PictureTag/src/main/user.txt";
            static int count=0;
            public void start() {      //一开始有一个用户
                UserInfo user=new UserInfo("admin","admin",null,0,null,null,0,0.0);
                Gson gson=new Gson();
                String gsonstring=gson.toJson(user);
                FileReadandWrite.WriteFile("PictureTag/src/main/user.txt", gsonstring);         //初始化用户
                UserInfo u = gson.fromJson(gsonstring,UserInfo.class);
                System.out.println(u.getLevel());
            }
            public ArrayList<UserInfo> getall(){
                ArrayList<String> content=FileReadandWrite.ReadFile("PictureTag/src/main/user.txt");         //获得所有的用户
                ArrayList<UserInfo> lis=new ArrayList<UserInfo>();
                for(int i=0;i<content.size();i++){
                    RevertJsonObject.getBean(content.get(i),UserInfo.class);
                }
                return lis;
            }
             public boolean login(String username,String password){
                if(count==0)
                    start();
                else
                    count++;
                      boolean flag=false;
                      ArrayList<String> content=FileReadandWrite.ReadFile(path);
                      ArrayList<UserInfo> user=new ArrayList<UserInfo>();
                      for(int i=0;i<content.size();i++){
                          Gson gson=new Gson();
                          user.add(gson.fromJson(content.get(i),UserInfo.class));
                      }
                      System.out.println(user.get(0).getUsername());
                      for(int i=0;i<user.size();i++){
                          if(username.equals(user.get(i).getUsername())){
                              if(password.equals(user.get(i).getPassword())){
                                  flag=true;
                              }
                                   break;             //去掉break会报空指针
                          }
                      }

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
                          FileReadandWrite.WriteFile("PictureTag/src/main/user.txt",RevertJsonObject.toJsonObject(user));
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
                               //删除文件
                               File f=new File(path);
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

package serviceimpl;

import com.google.gson.Gson;
import service.user;
import util.FileReadandWrite;
import vo.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class userserviceImpl implements user{
    //final String path="PictureTag/src/main/user.txt";
    //URL pathURL = Thread.currentThread().getContextClassLoader().getResource("");
    //final String path = "user.txt";
    final String path=UserInfo.class.getResource("/").getFile()+File.separator+"user.txt";
    public void start() {      //一开始有一个用户
        UserInfo user=new UserInfo("admin","admin","",0,new ArrayList<String>(),new ArrayList<String>(),0,0.0,new HashMap<String,Double>(),new HashMap<String, Boolean>());
        Gson gson=new Gson();
        String gsonstring=gson.toJson(user);
        FileReadandWrite.WriteFile(path, gsonstring);         //初始化用户
        UserInfo u = gson.fromJson(gsonstring,UserInfo.class);
        //System.out.println(u.getLevel());
    }
    public ArrayList<UserInfo> getall(){
        ArrayList<String> content=FileReadandWrite.ReadFile(path);         //获得所有的用户
        ArrayList<UserInfo> lis=new ArrayList<UserInfo>();
        for(int i=0;i<content.size();i++){
            Gson gson=new Gson();
            lis.add(gson.fromJson(content.get(i),UserInfo.class));
        }
        return lis;
    }
    public boolean login(String username, String password){
        if(!checkInit()) {
            start();
        }
        //System.out.println("login");
        boolean flag=false;
        ArrayList<String> content=FileReadandWrite.ReadFile(path);
        ArrayList<UserInfo> user=new ArrayList<UserInfo>();
        for(int i=0;i<content.size();i++){
            Gson gson=new Gson();
            user.add(gson.fromJson(content.get(i),UserInfo.class));
        }
        //System.out.println(user.get(0).getUsername());
        for(int i=0;i<user.size();i++){
            if(username.equals(user.get(i).getUsername())){
                if(password.equals(user.get(i).getPassword())){
                    flag=true;
                }
                break;             //去掉break会报空指针
            }
        }
        //System.out.println(flag);
        return flag;
    }
    public boolean register(String username,String password){
        boolean flag=true;
        if(checksame(username)) {
            flag = false;
            //System.out.println("Same!!");      //检验是否能测试出相同的用户名
        }
        else{
            flag=true;
            UserInfo user=new UserInfo(username,password,"",0,new ArrayList<String>(),new ArrayList<String>(),0,100,new HashMap<String,Double>(),new HashMap<String, Boolean>());
            Gson gson=new Gson();
            String content=gson.toJson(user);
            FileReadandWrite.WriteFile(path,content);
        }
        //System.out.println(flag);
        return  flag;
    }
    public boolean register(UserInfo userInfo){
        boolean flag=true;
        if(checksame(userInfo.getUsername())){
            flag=false;
        }
        else{
            flag=true;
            Gson gson=new Gson();
            String content=gson.toJson(userInfo);
            FileReadandWrite.WriteFile(path,content);
        }
        return flag;
    }
    public boolean delete(String username){
        boolean flag=true;
        if(!checksame(username)){
            flag=false;
            //System.out.println("non exist");
        }
        else{
            ArrayList<UserInfo> lis=getall();
            ArrayList<String> renew=new ArrayList<String>();
            for(UserInfo user:lis){
                if(user!=null&&!user.getUsername().equals(username)){
                    Gson gson=new Gson();
                    renew.add(gson.toJson(user));
                }
            }
            for(String str:renew){
                File f=new File(path);
                f.delete();
                for(int i=0;i<renew.size();i++){
                    FileReadandWrite.WriteFile(path,renew.get(i));
                }
            }
        }
        return  flag;
    }
    public boolean checkInit(){     //判断是否初始化过
        boolean flag=false;
        ArrayList<UserInfo> user=getall();
        for(int i=0;i<user.size();i++){
            if(user.get(i).getUsername().equals("admin")){
                flag=true;
                break;
            }
        }
        return flag;
    }
    public boolean checksame(String username){
        boolean flag=false;
        ArrayList<UserInfo> lis=getall();
        if(lis!=null) {
            for (UserInfo user : lis) {
                if (user!=null&&user.getUsername().equals(username)) {
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }
    public boolean update(String username,String password){
        boolean flag=false;
        if(checksame(username)) {
            flag=true;
            delete(username);
            //System.out.println("cc");
            register(username,password);
            //System.out.println("kk");
        }
        return flag;
    }
    public boolean update(UserInfo userInfo){
        boolean flag=false;
        if(checksame(userInfo.getUsername())){
            flag=true;
            delete(userInfo.getUsername());
            register(userInfo);
        }
        return flag;
    }
    public UserInfo getUser(String username){
        ArrayList<UserInfo> user=getall();
        UserInfo result=new UserInfo();
        for(int i=0;i<user.size();i++){
            if(user.get(i)!=null&&user.get(i).getUsername().equals(username)){
                result=user.get(i);
            }
        }
        return result;
    }
    //修改用户的等级
    public int updateLevel(double score){
        int level=0;
        if(score<10){
            level=1;
        }
        else if(score<30){
            level=2;
        }
        else if(score<60){
            level=3;
        }
        else if(score<100){
            level=4;
        }
        else{
            level=5;
        }
        return level;
    }
}

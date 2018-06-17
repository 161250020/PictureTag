package serviceimpl;

import com.google.gson.Gson;
import service.user;
import util.FileReadandWrite;
import vo.UserInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class userserviceImpl implements user{
    final String path=UserInfo.class.getResource("/").getFile()+File.separator+"user.txt";
    public void start() {      //一开始有一个用户
        UserInfo user=new UserInfo("admin","admin","",0,new ArrayList<String>(),new ArrayList<String>(),0,0.0,new HashMap<String,Double>(),new HashMap<String, Boolean>(),"管理员","","","");
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
            UserInfo user=new UserInfo(username,password,"",0,new ArrayList<String>(),new ArrayList<String>(),0,100,new HashMap<String,Double>(),new HashMap<String, Boolean>(),"","","","");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String date=format.format(new Date());
            String str1=date.substring(4,6);
            String str2=date.substring(6,8);
            user.setDate(date);
            user.setMonth(str1);
            user.setDay(str2);
            Gson gson=new Gson();
            String content=gson.toJson(user);
            FileReadandWrite.WriteFile(path,content);
        }
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
            File f=new File(path);
            f.delete();
            for(String str:renew){
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
            register(username,password);
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
    //每接受一个任务,就更新一次
    public void updatereceiveTask(String username,String taskId){
           UserInfo user=getUser(username);
           ArrayList<String> taskIds=user.getReceivetask();
           taskIds.add(taskId);
           user.setReceivetask(taskIds);
           update(user);
    }
    //用户给任务打分时,就更新一次
    public void updateEvalu(String username,String taskId,double score){             //打分的分数
           UserInfo user=getUser(username);
           Map<String,Double> temp=user.getReceiveEvalu();
           temp.put(taskId,score);
           user.setReceiveEvalu(temp);
           update(user);
    }
    //每放弃或完成一个任务就执行
    public void updatefinish(String username,String taskId,boolean finish){
           UserInfo user=getUser(username);
           Map<String,Boolean> temp=user.getFinish();
           temp.put(taskId,finish);
           if(finish){
               int number=user.getTaskNumber();
               number=number+1;
               user.setTaskNumber(number);
           }
           update(user);
    }
    //
    public void updatescore(String username,double score){
         UserInfo user=getUser(username);
         double newscore=user.getScore();
         newscore=newscore+score;
         user.setScore(newscore);
         int level=updateLevel(score);
         user.setLevel(level);
         update(user);
    }
}

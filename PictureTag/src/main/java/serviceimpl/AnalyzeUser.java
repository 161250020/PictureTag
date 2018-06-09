package serviceimpl;

import com.google.gson.Gson;
import service.Analyze;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;
import java.util.Map;


public class AnalyzeUser implements Analyze {
    userserviceImpl impl=new userserviceImpl();
    public ArrayList<UserInfo> calTurn(){
        ArrayList<UserInfo> all=impl.getall();
        for(int i=0;i<all.size();i++){
            for(int j=0;j<all.size();j++){
                if(all.get(i)!=null&&all.get(j)!=null&&all.get(i).getScore()>all.get(j).getScore()){
                    //System.out.println("111");
                    UserInfo temp=all.get(i);
                    all.set(i,all.get(j));
                    all.set(j,temp);
                }
            }
        }
        return all;
    }
    public int getSelf_Turn(String username){
        int result=0;
        ArrayList<UserInfo> newUser=calTurn();
        for(int i=0;i<newUser.size();i++){
            if(newUser.get(i).getUsername().equals(username)&&newUser.get(i)!=null){
                result=i+1;
            }
        }
        return result;
    }
    //5.31新增      更新工人的评价map
    public void updateEvalu(String username,String taskId,double score){
        UserInfo user=impl.getUser(username);
        Map<String,Double> receiveEvalu=user.getReceiveEvalu();
        receiveEvalu.put(taskId,score);
        user.setReceiveEvalu(receiveEvalu);
        impl.update(user);
    }
    /*
    可信度是不停的变动的,所以需要不停的调用,进行轮询
    */
    public double calTruth(String username){          //1.需要创建一个枚举类型,代表三种状态.(条件:每领取一个任务,就update一下user) 2.任务完成或放弃后,再更新
        double truth=0.0;
        UserInfo user=impl.getUser(username);
        Map<String,Boolean> finish=user.getFinish();
        int complete=0;
        int incomplete=0;
        for(boolean value:finish.values()){
            if(value){
                complete++;
            }
            else{
                incomplete++;
            }
            truth=(1.0)*complete/finish.size();
        }
        return truth;
    }
    public double correlation(String username){
        double result=0.0;
        return result;
    }
    public int getType1(String username){
        taskServiceImpl service=new taskServiceImpl();
        int count=0;
        UserInfo user=impl.getUser(username);
        ArrayList<String> receivepro=user.getReceivetask();
        Gson gson=new Gson();
        for(String str:receivepro){
            if(str!=null&&gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("area")){
                  count++;
            }
        }
        return count;
    }
    public int getType2(String username){
        taskServiceImpl service=new taskServiceImpl();
        int count=0;
        UserInfo user=impl.getUser(username);
        ArrayList<String> receivepro=user.getReceivetask();
        Gson gson=new Gson();
        for(String str:receivepro){
            if(str!=null&&gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("frame")){
                   count++;
            }
        }
        return count;
    }
    public int getType3(String username){
        taskServiceImpl service=new taskServiceImpl();
        int count=0;
        UserInfo user=impl.getUser(username);
        ArrayList<String> receivepro=user.getReceivetask();
        Gson gson=new Gson();
        for(String str:receivepro){
            if(str!=null&&gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("overall")){
                   count++;
            }
        }
        return count;
    }
    public String recommend(String username){
        String result="" ;
        if(getType1(username)>getType2(username)&&getType1(username)>getType3(username)){
            result="area";
        }
        else if(getType2(username)>getType1(username)&&getType2(username)>getType3(username)){
            result="frame";
        }
        else if(getType3(username)>getType1(username)&&getType3(username)>getType2(username)){
            result="overall";
        }
            return result;
    }
    public ArrayList<Task> recom(String username){
        ArrayList<Task> recommendTask=new ArrayList<Task>();
        return recommendTask;
    }
}

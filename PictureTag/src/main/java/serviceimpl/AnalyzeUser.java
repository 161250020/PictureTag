package serviceimpl;

import com.google.gson.Gson;
import service.Analyze;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;
import java.util.Map;


public class AnalyzeUser implements Analyze {
    userserviceImpl impl=new userserviceImpl();
    public ArrayList<UserInfo> calTurn(){                                            //返回一个排好序的用户积分序列
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
    public int getSelf_Turn(String username){                                       //该英雄的排名
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
    public double correlation(String username){                //相关性
        double result=0.0;
        return result;
    }
    public int getType1(String username){                    //用户完成area类型的任务数量
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
    public int getType2(String username){                    //用户完成frame类型的任务数量
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
    public int getType3(String username){                    //用户完成overall类型的任务数量
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
    public String recommend(String username){                    //获得推荐的类型
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
        else if(getType1(username)==getType2(username)&&getType3(username)<getType2(username)){
             result=compare(true,true,false,username);
        }
        else if(getType1(username)==getType3(username)&&getType2(username)<getType1(username)){
            result=compare(true,false,true,username);
        }
        else if(getType2(username)==getType3(username)&&getType1(username)<getType2(username)){
            result=compare(false,true,true,username);
        }
        else if(getType1(username)==getType2(username)&&getType2(username)==getType3(username)){
            result=compare(true,true,true,username);
        }
        else{}
            return result;
    }

    public String compare(boolean type1,boolean type2,boolean type3,String username){     //recomend方法调用,根据评分来获得推荐的种类
           String result="";
           String temp1="";
           String temp2="";
           String temp3="";
           double sum1=0.0;
           double sum2=0.0;
           double sum3=0.0;
           int count1=0;
           int count2=0;
           int count3=0;
           double average1=0.0;
           double average2=0.0;
           double average3=0.0;
           UserInfo user=impl.getUser(username);
           Map<String,Double> taskIds=user.getReceiveEvalu();
           if(type1){
               temp1="area";
           }
           if(type2){
               temp2="frame";
           }
           if(type3){
               temp3="overall";
           }
           if(temp1.equals("")){
               average1=0.0;
           }
           if(temp2.equals("")){
               average2=0.0;
           }
           if(temp3.equals("")){
               average3=0.0;
           }
           taskServiceImpl service=new taskServiceImpl();
           Gson gson=new Gson();
           for(String s:taskIds.keySet()){
                //判断task的类型
                if(gson.fromJson(service.receiveTaskInfo(s),Task.class).getTagType().equals("area")){
                        sum1=sum1+taskIds.get(s);
                        count1++;
                }
                if(gson.fromJson(service.receiveTaskInfo(s),Task.class).getTagType().equals("frame")){
                        sum2=sum2+taskIds.get(s);
                        count2++;
                }
                if(gson.fromJson(service.receiveTaskInfo(s),Task.class).getTagType().equals("overall")){
                        sum3=sum3+taskIds.get(s);
                        count3++;
                }
           }
           average1=sum1*1.0/count1;
           average2=sum2*1.0/count2;
           average3=sum3*1.0/count3;
           if(average1>average2&&average1>average3){
                result="area";
           }
           else if(average2>average1&&average2>average3){
               result="frame";
           }
           else if(average3>average1&&average3>average2){
               result="overall";
           }
           //未完待续
           else if(average1==average2&&average2==average3){

           }
           return result;
    }
    public ArrayList<Task> recom(String username){                         //返回推荐的具体任务
        String recommendType=recommend(username);
        ArrayList<Task> recommendTask=new ArrayList<Task>();
        //recommendTask先获得所有的任务            //然后根据type筛选出来

        return recommendTask;
    }
}

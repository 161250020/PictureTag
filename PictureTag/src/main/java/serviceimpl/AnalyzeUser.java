package serviceimpl;

import com.google.gson.Gson;
import service.Analyze;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;
import java.util.Map;


public class AnalyzeUser implements Analyze {                           //质量评估与推荐
    userserviceImpl impl=new userserviceImpl();
    public ArrayList<UserInfo> calTurn(){                                            //返回一个排好序的用户积分序列
        ArrayList<UserInfo> all=impl.getall();
        for(int i=0;i<all.size();i++){
            for(int j=0;j<all.size();j++){
                if(all.get(i)!=null&&all.get(j)!=null&&all.get(i).getScore()>all.get(j).getScore()){
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
        //添加期望值保存
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
    public String recommend(String username){                    //获得推荐的类型   推荐的优先级调度为接受任务数>评分>用户的完成情况
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
             result=comparebyEva(true,true,false,username);
        }
        else if(getType1(username)==getType3(username)&&getType2(username)<getType1(username)){
            result=comparebyEva(true,false,true,username);
        }
        else if(getType2(username)==getType3(username)&&getType1(username)<getType2(username)){
            result=comparebyEva(false,true,true,username);
        }
        else if(getType1(username)==getType2(username)&&getType2(username)==getType3(username)){
            result=comparebyEva(true,true,true,username);
    }
        else{}
            return result;
    }

    public String comparebyEva(boolean type1,boolean type2,boolean type3,String username){     //recomend方法调用,根据评分来获得推荐的种类
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
           //未完待续        每一种类型的完成度比较
           else if(average1==average2&&average2>average3){
               result=comparebyComplete(true,true,false,username);
           }
           else if(average1==average3&&average1>average2){
               result=comparebyComplete(true,false,true,username);
           }
           else if(average2==average3&&average3>average1){
               result=comparebyComplete(false,true,true,username);
           }
           else if(average1==average2&&average2==average3){
               result=comparebyComplete(true,true,true,username);
           }
           else{}
           return result;
    }
    public String comparebyComplete(boolean type1,boolean type2,boolean type3,String username){
           String result="";
           taskServiceImpl service=new taskServiceImpl();
           Gson gson=new Gson();
           UserInfo user=impl.getUser(username);
           Map<String,Boolean> finish=user.getFinish();
           int finishType1=0;
           int finishType2=0;
           int finishType3=0;
           int unfinishType1=0;
           int unfinishType2=0;
           int unfinishType3=0;
           for(String str:finish.keySet()){
               if(finish.get(str)){           //完成的任务
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("area")){
                       finishType1=finishType1+1;
                   }
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("frame")){
                       finishType2=finishType2+1;
                   }
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("overall")){
                       finishType3=finishType3+1;
                   }
               }
               if(!finish.get(str)){
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("area")){
                       unfinishType1=unfinishType1+1;
                   }
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("frame")){
                       unfinishType2=unfinishType2+1;
                   }
                   if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getTagType().equals("overall")){
                       unfinishType3=unfinishType3+1;
                   }
               }
           }
           int allType1=finishType1+unfinishType1;
           int allType2=finishType2+unfinishType2;
           int allType3=finishType2+unfinishType3;

           if(type1&&type2&&!type3){

           }
           if(type1&&type3&&!type2){

           }
           if(!type1&&type2&&type3){

           }
           if(type1&&type2&&type3){

           }
           return result;
    }

    public ArrayList<Task> recom(String username,String type){                         //返回推荐的具体任务
        taskServiceImpl service=new taskServiceImpl();
        String recommendType=recommend(username);
        ArrayList<Task> recommendTask=new ArrayList<Task>();
        if(recommendType.equals("all")){
            //通过servic获得所有的任务
        }
        else{
            //通过service获得推荐类型的任务
        }
        return recommendTask;
    }
    public double correlation(String username){                       //相关系数 Cov(X,Y)
        double result=0.0;
        double cov=0.0;
        double varX=0.0;
        double varY=0.0;
        return result;
    }
    public double ExpectedScore(String username){                    //会不断更新 (需不需要存储数据,便于作图),(新建一个数据类型)
        double result=0.0;
        userserviceImpl impl=new userserviceImpl();
        UserInfo user=impl.getUser(username);
        Map<String,Double> list=user.getReceiveEvalu();              //注意一点,完成的任务不一定有评分,因为打分和完成任务不是同步的
        int size=list.size();
        double sum=0.0;
        for(String str:list.keySet()){
            if(str!=null) {
                sum = sum + list.get(str);
            }
        }
        result=(1*1.0/size)*sum;
        return result;
    }
    public ArrayList<Double> relationbyScoreandEvalu(String username){             //(获得两个事件的概率P(A),P(B))完成事件关联度(优秀)    任务数+打分情况+得分奖励.(有多个事件)   (规定一个是好,一个是差)
        ArrayList<Double> result=new ArrayList<Double>();
        userserviceImpl impl=new userserviceImpl();
        UserInfo user=impl.getUser(username);
        //假设打分情况85为优秀
        int count1=0;
        double probability1=0.0;      //p(打分情况为优秀)
        Map<String,Double> list=user.getReceiveEvalu();
        for(String str:list.keySet()){
            if(str!=null){
                if(list.get(str)>=85){
                       count1++;
                }
            }
        }
        probability1=count1*1.0/list.size();
        result.add(probability1);

        int count2=0;
        double probabaility2=0.0;   //p(得分奖励高)
        //先获得有打分的任务(有完成的不一定有分);得分奖励,以10分为高.
        taskServiceImpl service=new taskServiceImpl();
        Gson gson=new Gson();
        ArrayList<String> getScoreList=new ArrayList<String>();
        for(String str:list.keySet()){
            if(str!=null) {
                getScoreList.add(str);
            }
        }
        for(String str:getScoreList){
            if(gson.fromJson(service.receiveTaskInfo(str),Task.class).getSocre()>=10&&str!=null){
                count2++;
            }
        }
        probabaility2=count2*1.0/getScoreList.size();
        result.add(probabaility2);
        return result;
    }
    public double SupportbyScoreandEvalu(String username) {           //P(AB)
        ArrayList<Double> result = new ArrayList<Double>();
        userserviceImpl impl = new userserviceImpl();
        Gson gson=new Gson();
        taskServiceImpl service=new taskServiceImpl();
        UserInfo user = impl.getUser(username);
        int count = 0;
        double probability=0.0;
        Map<String, Double> list = user.getReceiveEvalu();
        for (String str : list.keySet()) {
            if (str != null) {
                if(list.get(str)>=85&&gson.fromJson(service.receiveTaskInfo(str),Task.class).getSocre()>=10){
                    count++;
                }
            }
        }
        probability=count*1.0/list.size();
        return probability;
    }
    public double ConfidencebyScoreandEvalu(String username){           //P(A|B)
        ArrayList<Double> list=relationbyScoreandEvalu(username);
        double probality2=list.get(1);
        //求解P(A|B)=P(AB)/P(B)
        double result=0.0;
        result=SupportbyScoreandEvalu(username)/probality2;
        return result;
    }
   public double LiftbyScoreandEvalu(String username){                //P(A|B)/P(A)
       ArrayList<Double> list=relationbyScoreandEvalu(username);
       double probality1=list.get(0);
       double result=0.0;
       result=ConfidencebyScoreandEvalu(username)/probality1;
       return result;
   }
    //完成情况的关联度
}

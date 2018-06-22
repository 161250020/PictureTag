package serviceimpl.User;

import com.google.gson.Gson;
import serviceimpl.task.taskServiceImpl;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;
import java.util.Map;


public class AnalyzeUser implements service.AnalyzeUser {                           //质量评估与推荐
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
    public int getSelf_Turn(String username){                                       //该成员的的排名
        int result=0;
        ArrayList<UserInfo> newUser=calTurn();
        for(int i=0;i<newUser.size();i++){
            if(newUser.get(i).getUsername().equals(username)&&newUser.get(i)!=null){
                result=i+1;
            }
        }
        result=dealSameScore(result);
        return result;
    }
    //辅助方法
    public int dealSameScore(int result){
        int degree=0;
        ArrayList<UserInfo> list=calTurn();
        for(int i=0;i<result;i++){
             if(list.get(i).getScore()==list.get(result-1).getScore()){
                  degree=i+1;
                  break;
             }
        }
        return degree;
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
    用户的效率
    */
     public double calEffiency(String username) {                      //计算用户的权值效率(评估一个用户的能力)   (通过加权)
         taskServiceImpl service = new taskServiceImpl();
         Gson gson = new Gson();
         double effiency = 0.0;             //效率
         int counts = 0;                    //计算耗时量
         int realvalue = 0;                 //总权值
         UserInfo user = impl.getUser(username);
         Map<String, Boolean> finish = user.getFinish();
         for (String str : finish.keySet()) {
             if (finish.get(str)) {
                 Task temp = gson.fromJson(service.receiveTaskInfo(str), Task.class);
                 //System.out.println("完成日期"+" "+temp.getFinishDate());
                 String Date1 = temp.getReceiveDate();
                 String Date2 = temp.getFinishDate();
                 int day1 = calDate(Date1, Date2);                          //一个任务的耗时量
                 counts = counts + day1;
                 //System.out.println("耗时:"+" "+counts);
                 String Date3 = temp.getStartDate();
                 String Date4 = temp.getEndDate();
                 int day2 = calDate(Date3, Date4);
                 realvalue = realvalue + day2 * temp.getImageIds().size();    //该任务的权值,  需不需要乘以评分的百分比
                 //System.out.println("总权值:"+" "+realvalue);
             }
         }
         if(counts==0){
                effiency=0;
         }
         else{
                effiency=(realvalue*1.0)/(counts*1.0);
         }
         return effiency;
     }

     public boolean alarm(String username){                   //提示工人,他的效率有点问题
         boolean flag=false;
         double realvalue=calEffiency(username);       //考虑两种极端,只做好自己喜爱的,不喜爱的就是差评
         double truth=calTruth(username);              //是否有很多垃圾任务
         if(impl.getUser(username).getFinish().size()==0){
             return false;
         }
         if((realvalue*0.6+truth*0.4)<1){
             flag=true;
         }
         return flag;
     }


     //辅助方法,计算间隔时间
    public int calDate(String Date1,String Date2){
         int days=0;
         String temp1=Date1.substring(4,8);
         String temp2=Date2.substring(4,8);
         if(temp1.substring(0,2).equals(temp2.substring(0,2))){
               days=calDay(temp2.substring(2,4))-calDay(temp1.substring(2,4))+1;
         }
         else{
               int MonthOfDays=calDayOfMonth(temp1.substring(0,2));
               days=MonthOfDays-calDay(temp1.substring(2,4))+calDay(temp1.substring(2,4))+1;
         }
         return days;
    }

    /*
    可信度是不停的变动的,所以需要不停的调用,进行轮询
    */

    public double calTruth(String username){          //可信度
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
        if(count1!=0) {
            average1 = sum1 * 1.0 / count1;
        }
        else{
            average1=0;
        }
        if(count2!=0) {
            average2 = sum2 * 1.0 / count2;
        }
        else{
            average2=0;
        }
        if(count3!=0) {
            average3 = sum3 * 1.0 / count3;
        }
        else{
            average3=0;
        }

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

        double result1=0.0;
        double result2=0.0;
        double result3=0.0;
        if(allType1==0){
            result1=0.0;
        }
        else{
            result1=finishType1*1.0/allType1;
        }
        if(allType2==0){
            result2=0.0;
        }
        else{
            result2=finishType2*1.0/allType2;
        }
        if(allType3==0){
            result3=0.0;
        }
        else{
            result3=finishType3*1.0/allType3;
        }
        if(type1&&type2&&!type3){
            if(result1>result2){
                result="area";
            }
            else if(result1<result2){
                result="frame";
            }
            else{
                result="all";          //偷工减料一下
            }
        }
        if(type1&&type3&&!type2){
            if(result1>result3){
                result="area";
            }
            else if(result1<result3){
                result="overall";
            }
            else{
                result="all";
            }
        }
        if(!type1&&type2&&type3){
            if(result2>result3){
                result="frame";
            }
            else if(result2<result3){
                result="overall";
            }
            else{
                result="all";
            }
        }
        if(type1&&type2&&type3){
            result="all";
        }
        return result;
    }

    public ArrayList<Task> recom(String username){                         //返回推荐的具体任务
        taskServiceImpl service=new taskServiceImpl();
        String recommendType=recommend(username);
        ArrayList<Task> recommendTask=new ArrayList<Task>();
        ArrayList<String> temp=new ArrayList<String>();
        Gson gson=new Gson();
        if(recommendType.equals("all")){
            //通过servic获得所有的任务
            ArrayList<String> temp1=new ArrayList<String>();
            ArrayList<String> temp2=new ArrayList<String>();
            ArrayList<String> temp3=new ArrayList<String>();
            temp1=service.receiveTasks("area");
            temp2=service.receiveTasks("frame");
            temp3=service.receiveTasks("overall");
            for(String str:temp1){
                if(str!=null){
                    temp.add(str);
                }
            }
            for(String str:temp2){
                if(str!=null){
                    temp.add(str);
                }
            }
            for(String str:temp3){
                if(str!=null){
                    temp.add(str);
                }
            }
        }
        else{
            //通过service获得推荐类型的任务
            temp=service.receiveTasks(recommendType);
        }
        for(String str:temp){
            if(str!=null){
                recommendTask.add(gson.fromJson(str,Task.class));
            }
        }
        return recommendTask;
    }
    public double correlation(String username){                       //  相关系数 Cov(X,Y)/(x的标准差*y的标准差)  得分情况和奖励积分
        double result=0.0;
        double cov=0.0;                                               //协方差. cov(x,y)=E(xy)-E(x)*E(y)
        double varX=0.0;                                              //方差x
        double varY=0.0;                                              //方差y
        userserviceImpl impl=new userserviceImpl();
        UserInfo user=impl.getUser(username);
        Map<String,Double> Evalu=user.getReceiveEvalu();                //打分的map
        ArrayList<String> taskIds=new ArrayList<String>();             //接收到的打分taskId,获得对应的积分奖励
        ArrayList<Double> Evaluscores=new ArrayList<Double>();         //打分数组
        ArrayList<Double> scores=new ArrayList<Double>();              //得分奖励数组
        for(String str:Evalu.keySet()){
            taskIds.add(str);
            Evaluscores.add(Evalu.get(str));
        }
        taskServiceImpl service=new taskServiceImpl();
        Gson gson=new Gson();
        for(String str:taskIds){
            if(str!=null){
                scores.add(gson.fromJson(service.receiveTaskInfo(str),Task.class).getSocre());
            }
        }

        //计算E(x),E(y),E(xy)
        double u1=0.0;
        double u2=0.0;
        double u12=0.0;
        for(double score:Evaluscores){
            u1=u1+score;
        }
        if(Evaluscores.size()!=0) {
            u1 = u1 / Evaluscores.size();
        }
        else{
            u1=0;
        }

        for(double score:scores){
            u2=u2+score;
        }
        if(scores.size()!=0) {
            u2 = u2 / scores.size();
        }
        else{
            u2=0;
        }

        for(int i=0;i<Evaluscores.size();i++){
            u12=u12+Evaluscores.get(i)*scores.get(i);
        }
        if(scores.size()!=0) {
            u12 = u12 / scores.size();
        }
        else{
            u12=0;
        }
        //计算D(x),D(y)  即var(x),var(y)
        for(double score:Evaluscores){
            varX=varX+(score-u1)*(score-u1);
        }
        if(Evaluscores.size()!=0) {
            varX = varX / Evaluscores.size();
        }
        else{
            varX=0;
        }
        for(double score:scores){
            varY=varY+(score-u2)*(score-u2);
        }
        if(scores.size()!=0) {
            varY = varY / scores.size();
        }
        else{
            varY=0;
        }
        //计算cov    E(xy)-E(x)*E(y)
        cov=u12-u1*u2;
        if(varX!=0&&varY!=0) {
            result = cov / (Math.sqrt(varX) * Math.sqrt(varY));
        }
        else{
            result=0;
        }
        return result;
    }


    public double ExpectedScore(String username){                    //期望得分,会不断更新 (需不需要存储数据,便于作图),(新建一个数据类型)
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
        if(size!=0) {                                               //除0未考虑导致报错
            result = (1 * 1.0 / size) * sum;
        }
        else{
            result=0;
        }
        return result;
    }


    public ArrayList<Double> relationbyScoreandEvalu(String username){          //(获得两个事件的概率P(A),P(B))完成事件关联度(优秀)    任务数+打分情况+得分奖励.(有多个事件)   (规定一个是好,一个是差)
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
        if (list.size()!=0) {
            probability1 = count1 * 1.0 / list.size();
        }
        else{
            probability1=0;
        }
        result.add(probability1);

        int count2=0;
        double probabaility2=0.0;     //p(得分奖励高)
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
        if(getScoreList.size()!=0) {
            probabaility2 = count2 * 1.0 / getScoreList.size();
        }
        else{
            probabaility2=0;
        }
        result.add(probabaility2);
        return result;
    }
    public double SupportbyScoreandEvalu(String username) {           //P(AB)          支持度
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
        if(list.size()!=0) {
            probability = count * 1.0 / list.size();
        }
        else{
            probability=0;
        }
        return probability;
    }
    public double ConfidencebyScoreandEvalu(String username){           //P(A|B)      置信度
        ArrayList<Double> list=relationbyScoreandEvalu(username);
        double probality2=list.get(1);
        //求解P(A|B)=P(AB)/P(B)
        double result=0.0;
        if(probality2!=0) {
            result = SupportbyScoreandEvalu(username) / probality2;
        }
        else{
            result=0.0;
        }
        return result;
    }
    public double LiftbyScoreandEvalu(String username){                //P(A|B)/P(A)   作用度
        ArrayList<Double> list=relationbyScoreandEvalu(username);
        double probality1=list.get(0);
        double result=0.0;
        if(probality1!=0) {
            result = ConfidencebyScoreandEvalu(username) / probality1;
        }
        else{
            result=0;
        }
        return result;
    }



    //完成情况的关联度(积分和完成度的关系)
    public ArrayList<Double> relationbyScoreandFinish(String username){          //(获得两个事件的概率P(A),P(B)),完成事件关联度(优秀)
        ArrayList<Double> result=new ArrayList<Double>();
        userserviceImpl impl=new userserviceImpl();
        UserInfo user=impl.getUser(username);
        int count1=0;                    //计算完成度
        double probability1=0.0;         //p(完成)
        Map<String,Boolean> list=user.getFinish();
        for(String str:list.keySet()){
            if(str!=null){
                if(list.get(str)){
                    count1++;
                }
            }
        }
        if (list.size()!=0) {
            probability1 = count1 * 1.0 / list.size();
        }
        else{
            probability1=0;
        }
        result.add(probability1);

        int count2=0;
        double probabaility2=0.0;     //p(得分奖励高)
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
        if(getScoreList.size()!=0) {
            probabaility2 = count2 * 1.0 / getScoreList.size();
        }
        else{
            probabaility2=0;
        }
        result.add(probabaility2);
        return result;
    }

    public double SupportbyScoreandFinish(String username) {           //P(AB)          支持度
        ArrayList<Double> result = new ArrayList<Double>();
        userserviceImpl impl = new userserviceImpl();
        Gson gson=new Gson();
        taskServiceImpl service=new taskServiceImpl();
        UserInfo user = impl.getUser(username);
        int count = 0;
        double probability=0.0;
        Map<String, Boolean> list = user.getFinish();
        for (String str : list.keySet()) {
            if (str != null) {
                if(list.get(str)&&gson.fromJson(service.receiveTaskInfo(str),Task.class).getSocre()>=10){
                    count++;
                }
            }
        }
        if(list.size()!=0) {
            probability = count * 1.0 / list.size();
        }
        else{
            probability=0;
        }
        return probability;
    }
    public double ConfidencebyScoreandFinish(String username){               //P(A|B)      置信度
        ArrayList<Double> list=relationbyScoreandEvalu(username);
        double probality2=list.get(1);
        //求解P(A|B)=P(AB)/P(B)
        double result=0.0;
        if(probality2!=0) {
            result = SupportbyScoreandEvalu(username) / probality2;
        }
        else{
            result=0.0;
        }
        return result;
    }
    public double LiftbyScoreandFinish(String username){                //P(A|B)/P(A)   作用度
        ArrayList<Double> list=relationbyScoreandEvalu(username);
        double probality1=list.get(0);
        double result=0.0;
        if(probality1!=0) {
            result = ConfidencebyScoreandEvalu(username) / probality1;
        }
        else{
            result=0;
        }
        return result;
    }






    public int calDay(String str){                  //把日转换成天数
        int result=0;
        if(str.substring(0,1).equals("0")){
            result=Integer.parseInt(str.substring(1,2));
        }
        else{
            result=Integer.parseInt(str);
        }
        return result;
    }
    public int calDayOfMonth(String month){                    //每一个月的天数
        int days=0;
        if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")){
            days=31;
        }
        if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")){
            days=30;
        }
        if(month.equals("02")){
            days=28;
        }
        return days;
    }
}

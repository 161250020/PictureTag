import com.google.gson.Gson;
import serviceimpl.taskServiceImpl;
import serviceimpl.userserviceImpl;
import util.FileReadandWrite;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FindProjects implements service.FindProjects {
    public Project lauchPro(Project pro){
        String proId="";
        Date current=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");      //当前日期
        proId=pro.getUsername()+"_"+format.format(current);
        Project result=pro;
        result.setTaskIds(new ArrayList<String>());
        result.setList(new HashMap<String,String>());
        result.setRequests(new ArrayList<String>());
        result.setId(proId);                                                            //设置编号.
        String date=""+format.format(current);                                         //设置日期.
        result.setDate(date);
        Gson gson=new Gson();
        String gsonString=gson.toJson(result);
        String path= "src/main/test/"+pro.getUsername()+"_"+"Projects.txt";

        FileReadandWrite.WriteFile(path,gsonString);

        //更新user的lauchPro;
        userserviceImpl impl=new userserviceImpl();
        String username=pro.getUsername();
        UserInfo user=impl.getUser(username);
        ArrayList<String> temp=user.getLaunchpro();
        temp.add(result.getId());
        user.setLaunchpro(temp);
        impl.update(user);
        return result;
    }
    public ArrayList<Project> getPros(String username){
        userserviceImpl impl=new userserviceImpl();
        ArrayList<UserInfo> user=impl.getall();
        ArrayList<UserInfo> newUser=new ArrayList<UserInfo>();
        for(UserInfo u:user){
            if(u.getLaunchpro().size()>0&&!u.getUsername().equals(username)){
                newUser.add(u);
            }
        }
        ArrayList<Project> pro=new ArrayList<Project>();
        for(UserInfo u:newUser){
            for(Project p:getProjects(u.getUsername())){
                pro.add(p);
            }
        }
        return pro;
    }
    public ArrayList<Project> getProjects(String username){            //显示所用
        ArrayList<Project> list=new ArrayList<Project>();
        String path= FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt" ;                                       //路径未知.
        Gson gson=new Gson();
        ArrayList<String> temp=FileReadandWrite.ReadFile(path);
        for(String str:temp){
            if(str!=null){
                list.add(gson.fromJson(str,Project.class));
            }
        }
        return list;
    }
    public Project getProject(String proid){                       //给task调用通过proid获得project的内容
        Project pro=new Project();
        Gson gson=new Gson();
        String []split=proid.split("_");
        String username=split[0];
        String path= FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt" ;
        ArrayList<String> content=FileReadandWrite.ReadFile(path);
        for(String str:content){
            if(str!=null){
                if(gson.fromJson(str,Project.class).getId().equals(proid)) {               //智障的用了等于号
                    pro = gson.fromJson(str, Project.class);
                }
            }
        }
        return pro;
    }
    public ArrayList<Task> getTasks(String ProId){                       //点击id,获得对应的所有任务
        taskServiceImpl service=new taskServiceImpl();
        ArrayList<Task> tasks=new ArrayList<Task>();
        Project current=getProject(ProId);
        ArrayList<String> temp=current.getTaskIds();
        Gson gson=new Gson();
        for(String str:temp) {
            if (str != null) {
                tasks.add(gson.fromJson(service.receiveTaskInfo(str), Task.class));
            }
        }
        return tasks;
    }
    public ArrayList<Project> chooseProjectByDate(String Date1,String Date2,String username){                 //筛选发布时间内的project,添加到servelet里面
        ArrayList<Project> pro=getProjects(username);

        ArrayList<Project> result=new ArrayList<Project>();
        for(Project project:pro){
            if(checkDate1(Date1,project.getDate().substring(0,8))&&checkDate2(Date2,project.getDate().substring(0,8))){
                result.add(project);
            }
        }
        return result;
    }
    public boolean checkDate1(String Date1,String Date) {   //"yyyyMMddHHmmss"
        String Date1year = Date1.substring(0,4);              //可能有错误
        String Date1month = Date1.substring(4, 6);
        String Date1day = Date1.substring(6, 8);
        //String Date1hour=Date1.substring(8,10);
        //String Date1minute=Date1.substring(10,12);
        //String Date1second=Date1.substring(12,14);
        int Date1Year = Integer.parseInt(Date1year);
        int Date1Month = 0;                                    //这部分可以优化;写一个抽象函数
        if (Date1month.charAt(0) == '0') {
            Date1Month = Integer.parseInt(Date1month.substring(1, 2));
        } else {
            Date1Month = Integer.parseInt(Date1month);
        }
        int Date1Day = 0;
        if (Date1day.charAt(0) == '0') {
            Date1Day = Integer.parseInt(Date1day.substring(1, 2));
        } else {
            Date1Day = Integer.parseInt(Date1day);
        }
        //int Date1Hour=0;
        //if(Date1hour.charAt(0)=='0'){
        //Date1Hour=Integer.parseInt(Date1hour.substring(1,2));
        //}
        //else{
        //Date1Hour=Integer.parseInt(Date1hour);
        //}
        /*int Date1Minute=0;
        if(Date1minute.charAt(0)=='0'){
            Date1Minute=Integer.parseInt(Date1minute.substring(1,2));
        }
        else{
            Date1Minute=Integer.parseInt(Date1minute);
        }
        int Date1Second=0;
        if(Date1second.charAt(0)=='0'){
            Date1Second=Integer.parseInt(Date1second.substring(1,2));
        }
        else{
            Date1Second=Integer.parseInt(Date1second);
        }
       */
        String Dateyear = Date.substring(0,4);
        String Datemonth = Date.substring(4, 6);
        String Dateday = Date.substring(6, 8);
        //String Datehour=Date.substring(8,10);
        //String Dateminute=Date.substring(10,12);
        //String Datesecond=Date.substring(12,14);
        int DateYear = Integer.parseInt(Dateyear);
        int DateMonth = 0;                                    //这部分可以优化;写一个抽象函数
        if (Datemonth.charAt(0) == '0') {
            DateMonth = Integer.parseInt(Datemonth.substring(1, 2));
        } else {
            DateMonth = Integer.parseInt(Datemonth);
        }
        int DateDay = 0;
        if (Dateday.charAt(0) == '0') {
            DateDay = Integer.parseInt(Dateday.substring(1, 2));
        } else {
            DateDay = Integer.parseInt(Dateday);
        }
        /*int DateHour=0;
        if(Datehour.charAt(0)=='0'){
            DateHour=Integer.parseInt(Datehour.substring(1,2));
        }
        else{
            DateHour=Integer.parseInt(Datehour);
        }
        int DateMinute=0;
        if(Date1minute.charAt(0)=='0'){
            DateMinute=Integer.parseInt(Dateminute.substring(1,2));
        }
        else{
            DateMinute=Integer.parseInt(Dateminute);
        }
        int DateSecond=0;
        if(Datesecond.charAt(0)=='0'){
            DateSecond=Integer.parseInt(Datesecond.substring(1,2));
        }
        else{
            DateSecond=Integer.parseInt(Datesecond);
        }
        */

        //进行比较;
        if (Date1Year > DateYear) {
            return false;
        }
        if (Date1Year < DateYear) {
            return true;
        } else {
            if (Date1Month > DateMonth) {
                return false;
            }
            if (Date1Month < DateMonth) {
                return true;
            } else {
                if (Date1Day > DateDay) {
                    return false;
                }
                if (Date1Day < DateDay) {
                    return true;
                } else {
                    return true;
                }
            }
        }
    }
                /*else{
                    if(Date1Hour>DateHour){
                        return false;
                    }
                    if(Date1Hour<DateHour){
                        return true;
                    }
                    else{
                        if(Date1Minute>DateMinute){
                            return false;
                        }
                        if(Date1Minute<DateMinute){
                            return true;
                        }
                        else{
                            if(Date1Second>DateSecond){
                                return false;
                            }
                            if(Date1Second<DateSecond){
                                return true;
                            }
                            else{
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }
    */
    public boolean checkDate2(String Date2,String Date) {
        boolean flag = false;
        String Date2year = Date2.substring(0,4);
        String Date2month = Date2.substring(4, 6);
        String Date2day = Date2.substring(6, 8);
        //String Date2hour=Date2.substring(8,10);
        //String Date2minute=Date2.substring(10,12);
        //String Date2second=Date2.substring(12,14);
        int Date2Year = Integer.parseInt(Date2year);
        int Date2Month = 0;                                    //这部分可以优化;写一个抽象函数
        if (Date2month.charAt(0) == '0') {
            Date2Month = Integer.parseInt(Date2month.substring(1, 2));
        } else {
            Date2Month = Integer.parseInt(Date2month);
        }
        int Date2Day = 0;
        if (Date2day.charAt(0) == '0') {
            Date2Day = Integer.parseInt(Date2day.substring(1, 2));
        } else {
            Date2Day = Integer.parseInt(Date2day);
        }
        /*int Date2Hour=0;
        if(Date2hour.charAt(0)=='0'){
            Date2Hour=Integer.parseInt(Date2hour.substring(1,2));
        }
        else{
            Date2Hour=Integer.parseInt(Date2hour);
        }
        int Date2Minute=0;
        if(Date2minute.charAt(0)=='0'){
            Date2Minute=Integer.parseInt(Date2minute.substring(1,2));
        }
        else{
            Date2Minute=Integer.parseInt(Date2minute);
        }
        int Date2Second=0;
        if(Date2second.charAt(0)=='0'){
            Date2Second=Integer.parseInt(Date2second.substring(1,2));
        }
        else{
            Date2Second=Integer.parseInt(Date2second);
        }*/

        String Dateyear = Date.substring(0,4);
        String Datemonth = Date.substring(4, 6);
        String Dateday = Date.substring(6, 8);
        //String Datehour=Date.substring(8,10);
        //String Dateminute=Date.substring(10,12);
        //String Datesecond=Date.substring(12,14);
        int DateYear = Integer.parseInt(Dateyear);
        int DateMonth = 0;                                    //这部分可以优化;写一个抽象函数
        if (Datemonth.charAt(0) == '0') {
            DateMonth = Integer.parseInt(Datemonth.substring(1, 2));
        } else {
            DateMonth = Integer.parseInt(Datemonth);
        }
        int DateDay = 0;
        if (Dateday.charAt(0) == '0') {
            DateDay = Integer.parseInt(Dateday.substring(1, 2));
        } else {
            DateDay = Integer.parseInt(Dateday);
        }
        /*int DateHour=0;
        if(Datehour.charAt(0)=='0'){
            DateHour=Integer.parseInt(Datehour.substring(1,2));
        }
        else{
            DateHour=Integer.parseInt(Datehour);
        }
        int DateMinute=0;
        if(Dateminute.charAt(0)=='0'){
            DateMinute=Integer.parseInt(Dateminute.substring(1,2));
        }
        else{
            DateMinute=Integer.parseInt(Dateminute);
        }
        int DateSecond=0;
        if(Datesecond.charAt(0)=='0'){
            DateSecond=Integer.parseInt(Datesecond.substring(1,2));
        }
        else{
            DateSecond=Integer.parseInt(Datesecond);
        }*/
        //进行比较;
        if (Date2Year > DateYear) {
            return true;
        }
        if (Date2Year < DateYear) {
            return false;
        } else {
            if (Date2Month > DateMonth) {
                return true;
            }
            if (Date2Month < DateMonth) {
                return false;
            } else {
                if (Date2Day > DateDay) {
                    return true;
                }
                if (Date2Day < DateDay) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
                /*else{
                    if(Date2Hour>DateHour){
                        return true;
                    }
                    if(Date2Hour<DateHour){
                        return false;
                    }
                    else{
                        if(Date2Minute>DateMinute){
                            return true;
                        }
                        if(Date2Minute<DateMinute){
                            return false;
                        }
                        else{
                            if(Date2Second>DateSecond){
                                return false;
                            }
                            if(Date2Second<DateSecond){
                                return true;
                            }
                            else{
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }
    */
    public void update(Project pro){                       //界面修改任务
        String path= FindProjects.class.getResource("/").getFile()+ File.separator+"_"+pro.getUsername()+"_"+"Projects.txt";
        ArrayList<String> content=FileReadandWrite.ReadFile(path);
        ArrayList<String> current=new ArrayList<String>();
        Gson gson=new Gson();
        for(String str:content){
            if((!gson.fromJson(str,Project.class).getId().equals(pro.getId()))&&str!=null){             //==和equals    前后一致
                current.add(str);
            }
            if((gson.fromJson(str,Project.class).getId().equals(pro.getId()))&&str!=null){             //==和equals
                current.add(gson.toJson(pro));
            }
        }
        File file=new File(path);
        file.delete();
        for(String str:current) {
            if(str!=null) {
                FileReadandWrite.WriteFile(path, str);
            }
        }
    }
    //该方法用来提供生成任务时使用
    public void updateTaskId(String proId,String taskId){
        Project current=getProject(proId);
        ArrayList<String> taskIds=current.getTaskIds();
        taskIds.add(taskId);
        current.setTaskIds(taskIds);
        update(current);
    }
    //该方法用来提供给task完成时使用
    public void updateProgress(String proid){              //需要返回更新后的任务嘛;(有个麻烦:好像不能做到实时更新)
        Project current=getProject(proid);
        int pastprogress=current.getProgress();
        int currentprogress=pastprogress+1;
        current.setProgress(currentprogress);
        if(isfinish(currentprogress,current.getTaskIds().size())){                                   //判断
            current.setFinish(true);
        }
        update(current);
    }
    //领取任务时使用
    public void updateList(String proid,String username,String taskId){
        //更新pro
        Project current=getProject(proid);
        Map<String,String> past=current.getList();
        past.put(taskId,username);
        current.setList(past);
        update(current);
        //更新user
        userserviceImpl impl=new userserviceImpl();
        impl.updatereceiveTask(username,taskId);
    }
    public boolean isfinish(int currentprogress,int tasknumbers){                              //检验项目是否完成
        boolean finish=false;
        if(currentprogress==tasknumbers){
            finish=true;
        }
        return finish;
    }
}

package servlet;

import com.google.gson.Gson;
import service.imageService;
import service.taskService;
import serviceimpl.*;
import serviceimpl.Bill.BillServiceImpl;
import serviceimpl.tag.imageServiceImpl;
import serviceimpl.task.AdminUser;
import serviceimpl.FindProjects;
import serviceimpl.task.taskFilterServiceImpl;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.Task.image;
import vo.UserInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;


public class Servlet extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);//跳转到dopost执行
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//这个方法只对getPost()方法有用，详情见http://blog.csdn.net/joywy/article/details/8006645
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");//获取参数，当且仅当request中只有一个参数的时候有效
        if ("newUser".equals(action)) {
            String userData = request.getParameter("gData");
            this.newUser(request, response, userData);
        } else if ("login".equals(action)) {
            String userData = request.getParameter("gData");
            this.login(request, response, userData);
        } else if ("deleteUser".equals(action)) {
            String userId = request.getParameter("gData");
            this.deleteUser(request, response, userId);
        } else if ("modifyUser".equals(action)) {
            String userData = request.getParameter("gData");
            this.modifyUser(request, response, userData);
        } else if ("receiveUserInfo".equals(action)) {
            String userId = request.getParameter("gData");
            this.receiveUserInfo(request, response, userId);
        } else if ("receiveUserCount".equals(action)) {
            this.receiveUserCount(request, response);
        } else if ("receiveUserDegree".equals(action)) {
            String userId = request.getParameter("gData");
            this.receiveUserDegree(request, response, userId);
        } else if ("savePicture".equals(action)) {
            String imgData = request.getParameter("gData");
            this.savePicture(request, response, imgData);
        } else if ("receiveTag".equals(action)) {
            String picId = request.getParameter("gData");
            this.receiveTag(request, response, picId);
        } else if ("modifyTag".equals(action)) {
            String imgData = request.getParameter("gData");
            this.modifyTag(request, response, imgData);
        } else if ("acceptTask".equals(action)) {
            String taskId = request.getParameter("taskId");
            String userId = request.getParameter("userId");
            this.acceptTask(request, response, taskId, userId);
        } else if ("completeTask".equals(action)) {
            String taskId = request.getParameter("taskId");
            String userId = request.getParameter("userId");
            this.completeTask(request, response, taskId, userId);
        } else if ("confirmTask".equals(action)) {
            String taskId = request.getParameter("taskId");
            String userId = request.getParameter("userId");
            double grade = Double.valueOf(request.getParameter("grade"));
            this.confirmTask(request, response, taskId, userId, grade);
        } else if ("newTask".equals(action)) {
            String taskData = request.getParameter("gData");
            this.newTask(request, response, taskData);
        } else if ("receiveTaskContent".equals(action)) {
            String taskId = request.getParameter("gData");
            this.receiveTaskContent(request, response, taskId);
        } else if ("deleteTask".equals(action)) {
            String taskId = request.getParameter("gData");
            this.deleteTask(request, response, taskId);
        } else if ("receiveTaskId".equals(action)) {
            String userId = request.getParameter("gData");
            this.receiveTaskId(request, response, userId);
        } else if ("receiveCommittedTaskIds".equals(action)) {
            this.receiveCommittedTaskIds(request, response);
        } else if ("receiveTaskByDate".equals(action)) {
            String projectId = request.getParameter("projectId");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            this.receiveTaskByDate(request, response, projectId, startDate, endDate);
        } else if ("analyzeUser".equals(action)) {
            String user = request.getParameter("gData");
            this.analyzeUser(request, response, user);
        } else if ("receiveSingleRanking".equals(action)) {
            String usename = request.getParameter("gData");
            this.receiveSingleRanking(request, response, usename);
        } else if("calEffiency".equals(action)){
            String username=request.getParameter("gData");
            this.calEffiency(request,response,username);
        }
        else if ("calTruth".equals(action)) {
            String username = request.getParameter("gData");
            this.calTruth(request, response, username);
        }
        else if("alarm".equals(action)){
            String username=request.getParameter("gData");
            this.alarm(request, response, username);
        }
        else if ("recommend".equals(action)) {
            String username = request.getParameter("gData");
            this.recommend(request, response, username);
        } else if ("correlation".equals(action)) {
            String username = request.getParameter("gData");
            this.correlation(request, response, username);
        } else if ("ExpectedScore".equals(action)) {
            String username = request.getParameter("gData");
            this.ExpectedScore(request, response, username);
        } else if ("SupportbyScoreandEvalu".equals(action)) {
            String username = request.getParameter("gData");
            this.SupportbyScoreandEvalu(request, response, username);
        } else if ("ConfidencebyScoreandEvalu".equals(action)) {
            String username = request.getParameter("gData");
            this.ConfidencebyScoreandEvalu(request, response, username);
        } else if ("LiftbyScoreandEvalu".equals(action)) {
            String username = request.getParameter("gData");
            this.LiftbyScoreandEvalu(request, response, username);
        } else if ("launchPro".equals(action)) {
            String project = request.getParameter("gData");
            this.launchPro(request, response, project);
        } else if ("receivePros".equals(action)) {
            String username = request.getParameter("gData");
            this.receivePros(request, response, username);
        } else if ("receiveProjects".equals(action)) {
            String username = request.getParameter("gData");
            this.receiveProjects(request, response, username);
        } else if ("receiveProjectById".equals(action)) {
            String projectId = request.getParameter("gData");
            this.receiveProjectById(request, response, projectId);
        } else if ("chooseProjectByDate".equals(action)) {
            String Date1 = request.getParameter("Date1");
            String Date2 = request.getParameter("Date2");
            String username = request.getParameter("username");
            this.chooseProjectByDate(request, response, Date1, Date2, username);
        } else if ("receiveTasks".equals(action)) {
            String proId = request.getParameter("gData");
            this.receiveTasks(request, response, proId);
        }
        else if("downLoadTags".equals(action)){
            String taskId = request.getParameter("gData");
            this.downLoadTags(request,response,taskId);
        }else if ("recom".equals(action)) {
            String username = request.getParameter("gData");
            this.recom(request, response, username);
        } else if ("receiveAll".equals(action)) {
            this.receiveAll(request, response);
        } else if ("receiveUserCountByMonth".equals(action)) {
            String month = request.getParameter("gData");
            this.receiveUserCountByMonth(request, response, month);
        } else if ("receiveUserCountByYear".equals(action)) {
            String year = request.getParameter("gData");
            this.receiveUserCountByYear(request, response, year);
        } else if ("receiveProjectCountByMonth".equals(action)) {
            String month = request.getParameter("gData");
            this.receiveProjectCountByMonth(request, response, month);
        } else if ("receiveProjectCountByYear".equals(action)) {
            String year = request.getParameter("gData");
            this.receiveProjectCountByYear(request, response, year);
        }else if("receiveTaskCountByLevel".equals(action)){
            this.receiveTaskCountByLevel(request,response);
        }
        else if("receiveTaskCount".equals(action)){
            this.receiveTaskCount(request,response);
        }
        else if("receiveScoreByLevel".equals(action)){
            this.receiveScoreByLevel(request,response);
        }
        else if("receiveAllNorm".equals(action)){
            this.receiveAllNorm(request,response);
        }
        else if("BillTasks".equals(action)){
            String username=request.getParameter("gData");
            this.BillTasks(request,response,username);
        }
        else if("BillCounts".equals(action)){
            String username=request.getParameter("gData");
            this.BillCounts(request,response,username);
        }
        else {
            System.out.println("no function like this");
            String imgData = request.getParameter("gData");
            this.savePicture(request, response, imgData);
        }
    }

    /**
     * 注册用户
     * @param request
     * @param response
     * @param userData
     */
    private void newUser(HttpServletRequest request,HttpServletResponse response,String userData){
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.register(user.getUsername(),user.getPassword(),user.getNickname(),user.getName());
        try {
            PrintWriter out = response.getWriter();
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     * 用户登陆
     * @param request
     * @param response
     * @param userData
     */
    private void login(HttpServletRequest request,HttpServletResponse response,String userData){
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.login(user.getUsername(),user.getPassword());
        try {
            PrintWriter out = response.getWriter();
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     * 修改用户数据
     * @param request
     * @param response
     * @param userData
     */
    private void modifyUser(HttpServletRequest request,HttpServletResponse response,String userData){
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.update(user);
        try {
            PrintWriter out = response.getWriter();       //写入字符,不知道界面的键值是什么
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     * 通过id获取用户信息
     * @param request
     * @param response
     * @param userId
     */
    private void receiveUserInfo(HttpServletRequest request,HttpServletResponse response,String userId){
        UserInfo user=new UserInfo();
        userserviceImpl impl=new userserviceImpl();
        user=impl.getUser(userId);
        try {
            PrintWriter out = response.getWriter();       //写入字符,不知道界面的键值是什么
            Gson gson=new Gson();
            String result=gson.toJson(user);
            out.write(result);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**优先已完成
     * 根据用户id获得用户的：积分奖励，群体排名，等级
     * @param request
     * @param response
     * @param userId
     */
    private void receiveUserDegree(HttpServletRequest request,HttpServletResponse response,String userId){
        taskServiceImpl d = new taskServiceImpl();
        String out = d.receiveUserDegree(userId);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(out);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过id删除用户
     * @param request
     * @param response
     * @param userId
     */
    private void deleteUser(HttpServletRequest request,HttpServletResponse response,String userId){
        boolean result=false;
        userserviceImpl impl=new userserviceImpl();
        result=impl.delete(userId);
        try {
            PrintWriter out = response.getWriter();         //写入字符,不知道界面的键值是什么
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }
    /**
     *  获取用户总数
     * @param request
     * @param response
     * @param
     * @throws IOException
     */
    private void receiveUserCount(HttpServletRequest request,HttpServletResponse response){
        userserviceImpl impl=new userserviceImpl();
        ArrayList<UserInfo> user=impl.getall();
        int count=user.size();
        try{
            PrintWriter out=response.getWriter();
            String result=""+count;
            out.write(result);
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *  提供对单一图片的查询功能
     * @param request
     * @param response
     * @param s
     * @throws IOException
     */
    private void receiveTag(HttpServletRequest request,HttpServletResponse response,String s) throws IOException {
        String reqStr = s;

        imageServiceImpl t = new imageServiceImpl();
        String image = t.receiveTag(reqStr);

        try {
            PrintWriter out = response.getWriter();
            out.write(image);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片上传和保存，返回图片的ids
     * @param request
     * @param response
     * @param s
     */
    private void savePicture(HttpServletRequest request,HttpServletResponse response,String s){
        imageService t = new imageServiceImpl();
        String reqStr = s;

        Gson g = new Gson();
        image[] images = g.fromJson(s,image[].class);
        String out = t.saveTag(reqStr);

        try {
            PrintWriter p = response.getWriter();
            p.write(out);
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改对图片的标注内容
     * @param request
     * @param response
     */
    private void modifyTag(HttpServletRequest request,HttpServletResponse response,String imgData){
        imageService t = new imageServiceImpl();
        //System.out.println(imgData);
        //System.out.println("modifyTag");
        t.modifyTag(imgData);
        try {
            PrintWriter pw = response.getWriter();
            pw.write("true");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建task
     * @param request
     * @param response
     * @param taskData
     */
    private void newTask(HttpServletRequest request,HttpServletResponse response,String taskData){
        Gson g = new Gson();
        taskService d = new taskServiceImpl();
        d.newTask(taskData);
        try {
            PrintWriter p = response.getWriter();
            p.write("true");
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据任务(Task)id获得：标注人，图片列表(图片的ids)，图片的url，每张图片的标注内容
     * @param request
     * @param response
     * @param taskId
     */
    private void receiveTaskContent(HttpServletRequest request,HttpServletResponse response,String taskId){
        taskService d = new taskServiceImpl();
        //System.out.print("receiveTaskContent"+taskId);
        String taskInfo = d.receiveTaskInfo(taskId);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(taskInfo);
            //System.out.println(taskInfo);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传递任务(Task)的id给后台，可以删除该任务
     * @param request
     * @param response
     * @param taskId
     */
    private void deleteTask(HttpServletRequest request,HttpServletResponse response,String taskId){
        boolean b = true;
        boolean out1=false,out2=false;
        Gson gson = new Gson();
        taskServiceImpl d = new taskServiceImpl();

        String[] ss = taskId.split("_");
        String filePath1 = taskServiceImpl.class.getResource("/").getFile()+ File.separator+ss[0]+"_"+ss[1]+".task";
        String filePath2 = taskServiceImpl.class.getResource("/").getFile()+File.separator+"committedTask.task";
        String temp = d.findTask(taskId,filePath1);
        if(gson.fromJson(temp,Task.class).isReceive()){
            b = false;
        }
        else{
            out1 = d.deleteTask(taskId,filePath1);
            out2 = d.deleteTask(taskId,filePath2);
        }

        try {
            PrintWriter pw = response.getWriter();
            if(out1 && out2  && b){
                pw.write("true");
            }
            else{
                pw.write("false");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得下一个task的id
     * @param request
     * @param response
     * @param userId
     */
    private void receiveTaskId(HttpServletRequest request, HttpServletResponse response,String userId){
        taskService d = new taskServiceImpl();
        String taskId = d.getTaskId(userId);
        try {
            PrintWriter p = response.getWriter();
            p.write(taskId);
            //System.out.println(taskId);
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到已经发布的未被接受的任务列表
     * @param request
     * @param response
     */
    private void receiveCommittedTaskIds(HttpServletRequest request,HttpServletResponse response){
        taskServiceImpl d = new taskServiceImpl();
        ArrayList<String> ids = d.receiveCommittedTaskIds();
        try {
            PrintWriter pw = response.getWriter();
            for (int i = 0; i < ids.size(); i++) {
                pw.write(ids.get(i));
                pw.write(">_<");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据日期区间筛选出未被接受的task
     * @param request
     * @param response
     * @param taskId
     * @param startDate
     * @param endDate
     */
    private void receiveTaskByDate(HttpServletRequest request,HttpServletResponse response,String taskId,String startDate,String endDate){
        Gson gson = new Gson();
        taskFilterServiceImpl taskFilterService = new taskFilterServiceImpl();
        ArrayList<String> out = taskFilterService.findTaskByDate(taskId,startDate,endDate);
        String taskData = gson.toJson(out);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(taskData);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接受任务
     * @param request
     * @param response
     * @param taskId
     * @param userId
     */
    private void acceptTask(HttpServletRequest request,HttpServletResponse response,String taskId,String userId){
        taskService d = new taskServiceImpl();
        boolean b = d.acceptTask(taskId,userId);
        try {
            PrintWriter pw = response.getWriter();
            if(b){
                pw.write("true");
            }
            else{
                pw.write("false");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布者确认任务已经完成并给任务评分，任务最终完成
     * @param request
     * @param response
     * @param taskId
     * @param userId
     * @param grade
     */
    private void confirmTask(HttpServletRequest request,HttpServletResponse response,String taskId, String userId,double grade){
        taskService t = new taskServiceImpl();
        boolean b = t.confirmTask(taskId,userId,grade);
        try {
            PrintWriter pw = response.getWriter();
            if(b){
                pw.write("true");
            }
            else{
                pw.write("false");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * receiver确认完成task
     * @param request
     * @param response
     * @param taskId
     * @param userId
     */
    private void completeTask(HttpServletRequest request,HttpServletResponse response,String taskId,String userId){
        taskService d = new taskServiceImpl();
        boolean b = d.completeTask(taskId, userId);
        try {
            PrintWriter pw = response.getWriter();
            if(b){
                pw.write("true");
            }
            else{
                pw.write("false");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //AnalyzeUser
    private void receiveSingleRanking(HttpServletRequest request,HttpServletResponse response,String username){      //获得单个人的排名
        AnalyzeUser analyze=new AnalyzeUser();
        Gson gson=new Gson();
        int ranking=analyze.getSelf_Turn(username);
        try{
            PrintWriter out=response.getWriter();
            String result=""+ranking;
            out.write(result);
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void analyzeUser(HttpServletRequest request,HttpServletResponse response,String user){      //所有用户排名
        AnalyzeUser analyze=new AnalyzeUser();
        ArrayList<UserInfo> list=analyze.calTurn();
        Gson gson=new Gson();
        String gsondata=gson.toJson(list);
        try{
            PrintWriter p=response.getWriter();
            p.write(gsondata);
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void calEffiency(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser analyze=new AnalyzeUser();
        double effiency=analyze.calEffiency(username);
        try{
            PrintWriter p=response.getWriter();
            String result=""+effiency;
            p.write(result);
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private void calTruth(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser analyze=new AnalyzeUser();
        double truth=analyze.calTruth(username);
        try{
            PrintWriter p=response.getWriter();
            String result=""+truth;
            p.write(result);
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void alarm(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser analyze=new AnalyzeUser();
        boolean flag=analyze.alarm(username);
        try{
            PrintWriter p=response.getWriter();
            if(flag) {
                p.write("true");
            }
            else{
                p.write("false");
            }
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void recommend(HttpServletRequest request,HttpServletResponse response,String username){           //获得推荐类型
        AnalyzeUser impl=new AnalyzeUser();
        String type=impl.recommend(username);
        try{
            PrintWriter p=response.getWriter();
            p.write(type);
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void recom(HttpServletRequest request,HttpServletResponse response,String username){              //获得推荐任务
        AnalyzeUser impl=new AnalyzeUser();
        ArrayList<Task> list=impl.recom(username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(list);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void correlation(HttpServletRequest request,HttpServletResponse response,String username){       //相关系数
        AnalyzeUser impl=new AnalyzeUser();
        double temp=impl.correlation(username);
        String result=""+temp;
        try{
            PrintWriter writer=response.getWriter();
            writer.write(result);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void ExpectedScore(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser impl=new AnalyzeUser();
        double temp=impl.ExpectedScore(username);
        String result=""+temp;
        try{
            PrintWriter writer=response.getWriter();
            writer.write(result);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void SupportbyScoreandEvalu(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser impl=new AnalyzeUser();
        double temp=impl.SupportbyScoreandEvalu(username);
        String result=""+temp;
        try{
            PrintWriter writer=response.getWriter();
            writer.write(result);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void ConfidencebyScoreandEvalu(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser impl=new AnalyzeUser();
        double temp=impl.ConfidencebyScoreandEvalu(username);
        String result=""+temp;
        try{
            PrintWriter writer=response.getWriter();
            writer.write(result);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void LiftbyScoreandEvalu(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser impl=new AnalyzeUser();
        double temp=impl.LiftbyScoreandEvalu(username);
        String result=""+temp;
        try{
            PrintWriter writer=response.getWriter();
            writer.write(result);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    //FindProjects
    private void launchPro(HttpServletRequest request,HttpServletResponse response,String projectdata){        //发布任务
        FindProjects impl=new FindProjects();
        Gson gson=new Gson();
        Project pro=gson.fromJson(projectdata,Project.class);
        Project complete=impl.lauchPro(pro);
        String gsondata=gson.toJson(complete);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receivePros(HttpServletRequest request,HttpServletResponse response,String username){
        FindProjects impl=new FindProjects();
        ArrayList<Project> pro=impl.getPros(username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(pro);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveProjects(HttpServletRequest request,HttpServletResponse response,String username){              //显示用户的project
        FindProjects impl=new FindProjects();
        ArrayList<Project> pro=impl.getProjects(username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(pro);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveProjectById(HttpServletRequest request,HttpServletResponse response,String projectId){        //通过id获得project
        FindProjects impl=new FindProjects();
        Project pro=impl.getProject(projectId);
        Gson gson=new Gson();
        String gsondata=gson.toJson(pro);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void chooseProjectByDate(HttpServletRequest request,HttpServletResponse response,String Date1,String Date2,String username){
        FindProjects impl=new FindProjects();
        ArrayList<Project> pro=impl.chooseProjectByDate(Date1,Date2,username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(pro);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void receiveTasks(HttpServletRequest request,HttpServletResponse response,String proId){
        FindProjects impl=new FindProjects();
        ArrayList<Task> list=impl.getTasks(proId);
        Gson gson=new Gson();
        String gsondata=gson.toJson(list);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获得task标注的整合数据
     * @param request
     * @param response
     * @param taskId
     */
    private void downLoadTags(HttpServletRequest request,HttpServletResponse response,String taskId){
        String fileName = taskServiceImpl.class.getResource("/").getFile()+File.separator+taskId+"_images.txt";
        String contentType = this.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=a.txt";
        try {
            FileInputStream fis = new FileInputStream(new File(fileName));
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int len = 0;
            while ((len = fis.read(buf)) != -1){
                sb.append(new String(buf,0,len,"utf-8"));
            }
            response.setHeader("Content-Type",contentType);
            response.setHeader("Content-Disposition",contentDisposition);
            OutputStream output = response.getOutputStream();
            output.write(buf);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //adminuser
    public void receiveAll(HttpServletRequest request,HttpServletResponse response){                 //显示所有的用户
        AdminUser service=new AdminUser();
        ArrayList<UserInfo> list=service.getAll();
        Gson gson=new Gson();
        String gsondata=gson.toJson(list);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveUserCountByMonth(HttpServletRequest request,HttpServletResponse response,String month){
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getUserCountByMonth(month);
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveUserCountByYear(HttpServletRequest request,HttpServletResponse response,String year){
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getUserCountByYear(year);
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveProjectCountByMonth(HttpServletRequest request,HttpServletResponse response,String month){
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getProjectCountByMonth(month);
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveProjectCountByYear(HttpServletRequest request,HttpServletResponse response,String year) {
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getProjectCountByYear(year);
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void receiveTaskCountByLevel(HttpServletRequest request,HttpServletResponse response){
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getTaskCountByLevel();
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private  void receiveTaskCount(HttpServletRequest request,HttpServletResponse response){
        AdminUser service=new AdminUser();
        ArrayList<String> counts=service.getTaskCount();
        Gson gson=new Gson();
        String gsondata=gson.toJson(counts);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void receiveScoreByLevel(HttpServletRequest request,HttpServletResponse response){
        AdminUser service=new AdminUser();
        ArrayList<String> result=service.getScoreByLevel();
        Gson gson=new Gson();
        String gsondata=gson.toJson(result);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private  void receiveAllNorm(HttpServletRequest request,HttpServletResponse response){
        AdminUser service=new AdminUser();
        ArrayList<String> result=service.getAllNorm();
        Gson gson=new Gson();
        String gsondata=gson.toJson(result);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void BillTasks(HttpServletRequest request,HttpServletResponse response,String username){
        BillServiceImpl impl=new BillServiceImpl();
        ArrayList<String> tasks=impl.BillTasks(username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(tasks);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void BillCounts(HttpServletRequest request,HttpServletResponse response,String username){
        BillServiceImpl impl=new BillServiceImpl();
        ArrayList<String> result=impl.BillCounts(username);
        Gson gson=new Gson();
        String gsondata=gson.toJson(result);
        try{
            PrintWriter writer=response.getWriter();
            writer.write(gsondata);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

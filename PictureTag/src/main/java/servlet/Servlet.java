package servlet;

import com.google.gson.Gson;
import serviceimpl.*;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.Task.image;
import vo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
        if("newUser".equals(action)){
            String userData = request.getParameter("gData");
            this.newUser(request,response,userData);
        }
        else if("login".equals(action)){
            String userData = request.getParameter("gData");
            this.login(request,response,userData);
        }
        else if("deleteUser".equals(action)){
            String userId = request.getParameter("gData");
            this.deleteUser(request,response,userId);
        }
        else if("modifyUser".equals(action)){
            String userData = request.getParameter("gData");
            this.modifyUser(request,response,userData);
        }
        else if("receiveUserInfo".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveUserInfo(request,response,userId);
        }
/*        else if("receiveAllUser".equals(action)){
            this.receiveAllUser(request,response);
        }*/
        else if("receiveUserCount".equals(action)){
            this.receiveUserCount(request,response);
        }
        else if("receiveUserDegree".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveUserDegree(request,response,userId);
        }
        else if("savePicture".equals(action)){
            String imgData = request.getParameter("gData");
            this.savePicture(request,response,imgData);
        }
        else if("receiveTag".equals(action)){
            String picId = request.getParameter("gData");
            this.receiveTag(request,response,picId);
        }
        else if("modifyTag".equals(action)){
            String imgData = request.getParameter("gData");
            this.modifyTag(request,response,imgData);
        }
 /*       else if("receiveProjectInfo".equals(action)){
            String projectId = request.getParameter("gData");
            this.receiveProjectInfo(request,response,projectId);
        }
        else if("newProject".equals(action)){
            String projectData = request.getParameter("gData");
            this.newProject(request,response,projectData);
        }
        else if("modifyProject".equals(action)){
            String projectData = request.getParameter("gData");
            this.modifyProject(request,response,projectData);
        }
        else if("receiveProjects".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveProjects(request,response,userId);
        }*/
        else if("acceptTask".equals(action)){
            String taskId = request.getParameter("taskId");
            String userId = request.getParameter("userId");
            this.acceptTask(request,response,taskId,userId);
        }
        else if("completeTask".equals(action)){
            String taskId = request.getParameter("taskId");
            String userId = request.getParameter("userId");
            this.completeTask(request,response,taskId,userId);
        }
        else if("newTask".equals(action)){
            String taskData = request.getParameter("gData");
            this.newTask(request,response,taskData);
        }
        else if("receiveTaskContent".equals(action)){
            String taskId = request.getParameter("gData");
            this.receiveTaskContent(request,response,taskId);
        }
        else if("deleteTask".equals(action)){
            String taskId = request.getParameter("gData");
            this.deleteTask(request,response,taskId);
        }
/*        else if("commitTask".equals(action)){
            String taskId = request.getParameter("gData");
            this.commitTask(request,response,taskId);
        }*/
        else if("receiveTaskId".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveTaskId(request,response,userId);
        }
/*        else if("receiveImgId".equals(action)){
            String taskId = request.getParameter("gData");
            this.receiveImgId(request,response,taskId);
        }*/
        else if("analyzeUser".equals(action)){
            String user=request.getParameter("gData");
            this.analyzeUser(request,response,user);
        }
        else if("receiveCommittedTaskIds".equals(action)){
            this.receiveCommittedTaskIds(request,response);
        }
        else if("receiveSingleRanking".equals(action)){
            String usename = request.getParameter("gData");
            this.receiveSingleRanking(request,response,usename);
        }
        else if("launchPro".equals(action)){
            String project=request.getParameter("gData");
            this.launchPro(request,response,project);
        }
        else if("receiveProjects".equals(action)){
            String username=request.getParameter("gData");
            this.receiveProjects(request,response,username);
        }
        else if("chooseProjectByDate".equals(action)){
            String Date1=request.getParameter("Date1");
            String Date2=request.getParameter("Date2");
            String username=request.getParameter("username");
            this.chooseProjectByDate(request,response,Date1,Date2,username);
        }
        else if("receiveTasks".equals(action)){
            String proId=request.getParameter("gData");
            this.receiveTasks(request,response,proId);
        }
        else{
            System.out.println("no function like this");
            String imgData = request.getParameter("gData");
            this.savePicture(request,response,imgData);
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
        result=impl.register(user.getUsername(),user.getPassword());                 //userdata到底是什么
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

    private void login(HttpServletRequest request,HttpServletResponse response,String userData){     //这里会不会有问题
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.login(user.getUsername(),user.getPassword());
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
     * 根据用户id获得所有任务(任务id+基本信息+参与者+完成进度)，已完成任的任务(任务id+基本信息+参与者+完成进度)，未完成任务(任务id+基本信息+参与者+完成进度)
     * @param request
     * @param response
     * @param userId
     */
    private  void receiveUserTask(HttpServletRequest request,HttpServletResponse response,String userId){

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
    private void receiveSingleRanking(HttpServletRequest request,HttpServletResponse response,String username){
        AnalyzeUser analyze=new AnalyzeUser();
        Gson gson=new Gson();
        int ranking=analyze.getSelf_Turn(username);
        //System.out.println(realname);
        try{
            PrintWriter out=response.getWriter();
            String result=""+ranking;
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
        System.out.println(s);
        imageServiceImpl t = new imageServiceImpl();
        String image = t.receiveTag(reqStr);
        System.out.println(image);
        try {
            PrintWriter out = response.getWriter();
            out.write(image);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void savePicture(HttpServletRequest request,HttpServletResponse response,String s){
        imageServiceImpl t = new imageServiceImpl();
        String reqStr = s;
        //System.out.println("call");
        //System.out.println(s);

        Gson g = new Gson();
        image i = g.fromJson(reqStr,image.class);
        //System.out.println(i.getId());

        String out = t.writeTag(reqStr);
        //System.out.println(out);
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
        imageServiceImpl t = new imageServiceImpl();
        System.out.println(imgData);
        System.out.println("modifyTag");
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
     *暂无
     * @param request
     * @param response
     */
    private void receiveProjectInfo(HttpServletRequest request,HttpServletResponse response,String projectId){

    }

    /**
     * 暂无
     * 新增project，输入为project对象的完整信息
     * @param request
     * @param response
     */
    private  void newProject(HttpServletRequest request,HttpServletResponse response,String projectData){

    }

    /**
     * 暂无
     * 修改project，输入为project对象的完整信息
     * @param request
     * @param response
     * @param projectData
     */
    private void modifyProject(HttpServletRequest request,HttpServletResponse response,String projectData){

    }


    /**
     * 暂无
     * @param request
     * @param response
     * @param projectId
     */
    private  void deleteProject(HttpServletRequest request,HttpServletResponse response,String projectId){

    }

    private void newTask(HttpServletRequest request,HttpServletResponse response,String taskData){
        //System.out.println(taskData);
        Gson g = new Gson();
        //System.out.println(g.fromJson(taskData,Task.class).getId());
        taskServiceImpl d = new taskServiceImpl();
        d.newTask(taskData);
        try {
            PrintWriter p = response.getWriter();
            p.write("true");
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**优先
     * 王灿灿
     * 根据任务(Task)id获得：标注人，图片列表(图片的ids)，图片的url，每张图片的标注内容
     * @param request
     * @param response
     * @param taskId
     */
    private void receiveTaskContent(HttpServletRequest request,HttpServletResponse response,String taskId){
        taskServiceImpl d = new taskServiceImpl();
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


    /**王灿灿
     * 传递任务(Task)的id给后台，可以删除该任务
     * @param request
     * @param response
     * @param taskId
     */
    private void deleteTask(HttpServletRequest request,HttpServletResponse response,String taskId){
        taskServiceImpl d = new taskServiceImpl();
        String[] ss = taskId.split("&");
        String filePath = taskServiceImpl.class.getResource("/").getFile()+ File.separator+ss[0]+".txt";
        d.deleteTask(taskId,filePath);
    }

/*    private void commitTask(HttpServletRequest request, HttpServletResponse response,String taskId){
        taskServiceImpl d = new taskServiceImpl();
        d.commitTask(taskId);
    }*/

    private void receiveTaskId(HttpServletRequest request, HttpServletResponse response,String userId){
        taskServiceImpl d = new taskServiceImpl();
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

/*    private void receiveImgId(HttpServletRequest request,HttpServletResponse response,String taskId){
        imageServiceImpl t = new imageServiceImpl();
        String imgId = t.receiveImgId(taskId);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(imgId);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void analyzeUser(HttpServletRequest request,HttpServletResponse response,String user){
        AnalyzeUser analyze=new AnalyzeUser();
        ArrayList<UserInfo> list=analyze.calTurn();
        try{
            PrintWriter p=response.getWriter();
            for(int i=0;i<list.size();i++){
                if(list.get(i)!=null){
                    Gson gson=new Gson();
                    String gsonString=gson.toJson(list.get(i));
                    p.write(gsonString);
                }
            }
            p.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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

    private void acceptTask(HttpServletRequest request,HttpServletResponse response,String taskId,String userId){
        taskServiceImpl d = new taskServiceImpl();
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

    private void completeTask(HttpServletRequest request,HttpServletResponse response,String taskId,String userId){
        taskServiceImpl d = new taskServiceImpl();
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

   private void launchPro(HttpServletRequest request,HttpServletResponse response,String projectdata){
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

   private void receiveProjects(HttpServletRequest request,HttpServletResponse response,String username){
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
/*
    private  void receiveAllUser(HttpServletRequest request,HttpServletResponse response){
        taskServiceImpl d = new taskServiceImpl();
        ArrayList<String> out = d.receiveAllUserIds();
        try {
            PrintWriter pw = response.getWriter();
            for (int i = 0; i < out.size(); i++) {
                pw.write(out.get(i));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/


}

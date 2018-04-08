package servlet;

import serviceimpl.tagIO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String reqStr = request.getParameter("name");
        if("writeTag".equals(action)){
            this.writeTag(request,response,reqStr);
        }
        else if("receiveTag".equals(action)){
            System.out.print(reqStr);
            this.receiveTag(request,response,reqStr);
        }
        else if("modifyTag".equals(action)){
            this.modifyTag(request,response);
        }
        else if("getProjectInfo".equals(action)){
            this.getProjectInfo(request,response);
        }
        else if("getProjects".equals(action)){
            this.getProjects(request,response);
        }
        else if("getTask".equals(action)){
            this.getTask(request,response);
        }
        else{
            System.out.println("no function like this");
        }
    }

    //单一图片查询功能
    private void receiveTag(HttpServletRequest request,HttpServletResponse response,String s) throws IOException {
        String reqStr = s;
        tagIO t = new tagIO();
        ArrayList<String> images = t.receiveTag(reqStr);
        try {
            PrintWriter out = response.getWriter();
            for (int i = 0; i < images.size(); i++) {
                out.write(images.get(i));
            }
            out.close();//这个也会将缓冲区的数据输出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTag(HttpServletRequest request,HttpServletResponse response,String s){
        tagIO t = new tagIO();
        String reqStr = s;
        boolean flag = t.writeTag(reqStr);
    }

    private void modifyTag(HttpServletRequest request,HttpServletResponse response){

    }

    private void getProjectInfo(HttpServletRequest request,HttpServletResponse response){

    }

    private  void getProjects(HttpServletRequest request,HttpServletResponse response){

    }

    private void getTask(HttpServletRequest request,HttpServletResponse response){

    }

}

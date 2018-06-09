package serviceimpl;

import com.google.gson.Gson;
import util.FileReadandWrite;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FindProjects implements service.FindProjects {
    public void lauchPro(Project pro){
        String proId="";
        Date current=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");      //当前日期
        proId=pro.getUsername()+"_"+format.format(current);
        pro.setId(proId);                              //设置编号.
        String date=""+format.format(current);         //设置日期
        pro.setDate(date);
        Gson gson=new Gson();
        String gsonString=gson.toJson(pro);
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+pro.getUsername()+"_"+"Projects.txt";                                      //路径未填写
        FileReadandWrite.WriteFile(path,gsonString);
    }
    public void deletePro(String proId){
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+pro.getUsername()+"_"+"Projects.txt";                                     //路径未填写
        Gson gson=new Gson();
        ArrayList<String> content=FileReadandWrite.ReadFile(path);
        ArrayList<String> current=new ArrayList<String>();
        for(String str:content){
            if(str!=null&&gson.fromJson(str,Project.class).getId()!=proId){
                current.add(str);
            }
        }
        for(String str:current){
            if(str!=null){
                FileReadandWrite.WriteFile(path,str);
            }
        }
    }
    public ArrayList<Project> getProjects(String username){
        ArrayList<Project> list=new ArrayList<Project>();
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt" ;                                       //路径未知.
        Gson gson=new Gson();
        ArrayList<String> temp=FileReadandWrite.ReadFile(path);
        for(String str:temp){
            if(str!=null){
                list.add(gson.fromJson(str,Project.class));
            }
        }
        return list;
    }
    public ArrayList<projectInfo> getProjectInfo(){                //先获得所有的路径,再判断文件是否存在,全都读取出来     先不管这个方法
        return null;
    }               //获得任务得大致信息.
    /*public Task getTask(String username,String id){
        taskServiceImpl impl=new taskServiceImpl();
        Task task=new Task();
        Gson gson=new Gson();
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt";
        ArrayList<String>  content=FileReadandWrite.ReadFile(path);     //读出该用户的所有任务
        //查找到指定的project.
        Project pro=new Project();
        for(String str:content){
            if(str!=null&&gson.fromJson(str,Project.class).getId()==id){
                pro=gson.fromJson(str,Project.class);
                break;
            }
        }
        ArrayList<String> taskIds=new ArrayList<String>();
        taskIds=pro.getTaskIds();
        return task;
    }    //获得project展开得所有task信息
    */
}

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
        Gson gson=new Gson();
        String gsonString=gson.toJson(pro);
        String path="";                                      //路径未填写
        FileReadandWrite.WriteFile(path,gsonString);
    }
    public void deletePro(String proId){
        String path=taskServiceImpl.class.getResource("/").getFile()+ File.separator+"";                                     //路径未填写
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
    public ArrayList<Project> getProjects(String username, boolean b){
        ArrayList<Project> list=new ArrayList<Project>();
        String path="";                                       //路径未知.
        Gson gson=new Gson();
        ArrayList<String> temp=FileReadandWrite.ReadFile(path);
        for(String str:temp){
            if(str!=null){
                list.add(gson.fromJson(str,Project.class));
            }
        }
        return list;
    }
    public ArrayList<projectInfo> getProjectInfo(){                //先获得所有的路径,再判断文件是否存在,全都读取出来
        return null;
    }               //获得任务得大致信息.
    public ArrayList<Task> getTask(String usename,String id){
        ArrayList<Task> taskInfo=new ArrayList<Task>();
        Gson gson=new Gson();
        String path="";                                         //路径空
        ArrayList<String>  content=FileReadandWrite.ReadFile(path);     //读出该用户的所有任务
        //查找到指定的project.
        Project pro=new Project();
        for(String str:content){
            if(str!=null&&gson.fromJson(str,Project.class).getId()==id){
                pro=gson.fromJson(str,Project.class);
                break;
            }
        }

        return taskInfo;
    }    //获得project展开得所有task信息.
}

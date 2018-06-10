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
    public Project lauchPro(Project pro){
        String proId="";
        Date current=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");      //当前日期
        proId=pro.getUsername()+"_"+format.format(current);
        Project result=pro;
        result.setId(proId);                              //设置编号.
        String date=""+format.format(current);         //设置日期.
        result.setDate(date);
        Gson gson=new Gson();
        String gsonString=gson.toJson(result);
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+pro.getUsername()+"_"+"Projects.txt";                                      //路径未填写
        FileReadandWrite.WriteFile(path,gsonString);
        return result;
    }
    /*public void deletePro(String username,String proId){
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt";                                     //路径未填写
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
    */
    public ArrayList<Project> getProjects(String username){            //显示所用
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
    public Project getProject(String proid){                       //给task调用通过proid获得project的内容
        Project pro=new Project();
        Gson gson=new Gson();
        String []split=proid.split("_");
        String username=split[0];
        String path=FindProjects.class.getResource("/").getFile()+ File.separator+"_"+username+"_"+"Projects.txt" ;
        ArrayList<String> content=FileReadandWrite.ReadFile(path);
        for(String str:content){
            if(str!=null&&gson.fromJson(str,Project.class).getId()==proid){
                  pro=gson.fromJson(str,Project.class);
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
        for(String str:temp){
            if(str!=null){
                tasks.add(gson.fromJson(service.receiveTaskInfo(str),Task.class));
            }
        }
        return tasks;
    }
    public ArrayList<Project> choose(String Date1,String Date2,String username){                 //筛选发布时间内的project
        ArrayList<Project> pro=new ArrayList<Project>();
        return pro;
    }
    public boolean checkDate1(String Date1){
        boolean flag=false;
        return flag;
    }
    public boolean checkDate2(String Date2){
        boolean flag=false;
        return flag;
    }
    /*public ArrayList<projectInfo> getProjectInfo(){                //先获得所有的路径,再判断文件是否存在,全都读取出来     先不管这个方法
        return null;
    }
    */             //获得任务得大致信息.
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

package serviceimpl;

import com.google.gson.Gson;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.io.*;
import java.util.ArrayList;

public class dataAnalyze {

    String userFileName="";
    String taskFileName="";


    /**
     * 根据用户id获得用户的：积分奖励，群体排名，等级
     * @param userId
     */
    public ArrayList<String> receiveUserDegree(String userId) {

        return checkUserInfo(userId);

    }

    /**
     * 根据projectId获得projectInfo
     * @param projectId
     * @return
     */
    public String receiveProjectInfo(String projectId){

        Gson gson = new Gson();
        String[] strings = projectId.split("^_^");
        String userId = strings[0];
        File f = new File(userId);
        String out = "";
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while(null != (temp = br.readLine())){
                Project p = gson.fromJson(temp,Project.class);
                if(p.getId().equals(projectId)){
                    out = temp;
                    break;
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 需要调用userserviceImpl的update方法，并没有被完全实现
     * @param projectData
     */
    public void newProject(String projectData){

        userserviceImpl userserv = new userserviceImpl();
        Gson gson = new Gson();
        Project p = gson.fromJson(projectData,Project.class);
        String userId = p.getId().split("^_^")[0];

        /*userserv.update();*/

        File projectFile = new File(userId);
        try {
            FileWriter fw = new FileWriter(projectFile,true);
            fw.write(System.getProperty("line.separator"));
            fw.write(projectData);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void deleteProject(String projectId){

        /*这里要调用一下修改user.txt的方法*/

        /*这里要调用一下修改project文件内容的方法*/

        /*这里要调用删除task的方法*/

    }

    public void deleteTask(String taskId){



    }

    /**
     * 等待讨论确定！
     * @param userId
     * @return
     */
    public ArrayList<String> receiveUserTask(String userId){

        Gson gson = new Gson();

        ArrayList<String> userInfos = checkUserInfo(userId);
        UserInfo userInfo = gson.fromJson(userInfos.get(0),UserInfo.class);
        ArrayList<String> receivepro = userInfo.getReceivepro();
        ArrayList<String> launchpro = userInfo.getLaunchpro();



        return null;
    }

    /**
     * 根据taskId返回对应的图片列表，但是tagIO类里的receive
     * @param taskId
     * @return
     */
    public Task receiveTaskContent(String taskId){

        tagIO t = new tagIO();
        ArrayList<String> images = new ArrayList<>();

        return null;
    }

    /**
     *
     * @param taskJson
     */
    public void newTask(String taskJson){

        Gson gson = new Gson();
        Task t = gson.fromJson(taskJson,Task.class);
        String filename = t.getId();
        String[] strings = filename.split("^_^");
        File p = new File(strings[0]+"^_^"+strings[1]);

        File f = new File(filename);
        //创建名为taskId的文件用于存储图片
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 对文件的重新读写实现删除
     * @param taskId
     */
    public void deleteTasks(String taskId){

        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        String[] ss = taskId.split("^_^");
        String projectId = ss[0]+"^_^"+ss[1];
        File fRead = new File(taskId);
        File fWrite = new File(taskId);
        try {
            FileReader fr = new FileReader(fRead);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                Task task = gson.fromJson(temp, Task.class);//反序列化
                if(!task.getId().equals(taskId)){
                    reWrite.add(temp);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fRead.delete();

        if(!fWrite.exists()){
            try {
                fWrite.createNewFile();
                FileWriter fw = new FileWriter(fWrite);
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < reWrite.size() ; i++) {
                    bw.write(reWrite.get(i));
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private ArrayList<String> checkUserInfo(String userId){
        ArrayList<String> out = new ArrayList<>();

        File user = new File(userFileName);
        Gson gson = new Gson();

        try {
            FileReader fr = new FileReader(user);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                UserInfo u = gson.fromJson(temp, UserInfo.class);//反序列化
                if(u.getName().equals(userId)){
                    out.add(temp);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private  ArrayList<String> checkTask(String projectId){

        ArrayList<String> out = new ArrayList<>();
        int length = projectId.length();
        File task = new File(taskFileName);
        Gson gson = new Gson();

        try {
            FileReader fr = new FileReader(task);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                Task t = gson.fromJson(temp,Task.class);
                if(t.getId().substring(0,length-1).equals(projectId)){
                    out.add(temp);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
        }



}

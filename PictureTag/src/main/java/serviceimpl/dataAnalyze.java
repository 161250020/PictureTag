package serviceimpl;

import com.google.gson.Gson;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.io.*;
import java.util.ArrayList;

public class dataAnalyze {

    String taskFileName="";


    /**
     * 根据用户id获得用户的：积分奖励，群体排名，等级
     * @param userId
     */
    public String receiveUserDegree(String userId) {

        return checkUserInfo(userId);

    }

    /**
     * 暂时不用
     * 根据projectId获得projectInfo
     * @param TaskId
     * @return
     */
    public String receiveTaskInfo(String TaskId){

        Gson gson = new Gson();
        String[] strings = TaskId.split("^_^");
        String userId = strings[0];
        File f = new File(userId);
        String out = "";
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while(null != (temp = br.readLine())){
                Task p = gson.fromJson(temp,Task.class);
                if(p.getId().equals(TaskId)){
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
     * 暂时不用
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

    /**
     * 暂时不用
     * @param projectId
     */
    public void deleteProject(String projectId){

        /*这里要调用一下修改user.txt的方法*/

        /*这里要调用一下修改project文件内容的方法*/

        /*这里要调用删除task的方法*/

    }

    /**
     * 这个方法被舍弃了！
     * @param userId
     * @return
     */
    public ArrayList<String> receiveUserTask(String userId){

        Gson gson = new Gson();

        /*ArrayList<String> userInfos = checkUserInfo(userId);
        UserInfo userInfo = gson.fromJson(userInfos.get(0),UserInfo.class);
        ArrayList<String> receivepro = userInfo.getReceivepro();
        ArrayList<String> launchpro = userInfo.getLaunchpro();*/



        return null;
    }

    /**
     *
     * @param  projectId
     * @return
     */
    public String receiveProjectInfo(String projectId){


        return null;
    }

    /**
     *
     * @param
     */
    /*public void newTask(String taskJson){

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
*/


    /**
     * 获得下一个taskId的方法
     * @param userId
     * @return
     */
    public String getTaskId(String userId){
        Gson gson = new Gson();
        File f = new File(userId);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader fr = null;
        String out = "";
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            String temp1 = "";
            while(null != (temp = br.readLine())){
                temp1 = temp;
            }
            if(!temp1.equals("")) {
                Task t = gson.fromJson(temp1, Task.class);
                String preTaskId = t.getId().split("^_^")[1];
                int count = 0;
                for (int i = 0; i < preTaskId.length(); i++) {
                    char c = preTaskId.charAt(i);
                    if (c == '0') {
                        count++;
                    }
                }
                String nowId = preTaskId.substring(count);
                String id = String.valueOf(Integer.getInteger(nowId) + 1);
                out = "";
                for (int i = 0; i < 5 - id.length(); i++) {
                    out = "0" + out;
                }
            }
            else{
                return userId+"^_^"+"00001";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    public void newTask(String taskJson){
        Gson gson = new Gson();
        Task t = gson.fromJson(taskJson,Task.class);
        String filename = t.getId();
        String[] strings = filename.split("^_^");
        String fileName = dataAnalyze.class.getResource("/").getFile()+File.separator+strings[0]+".txt";
        File f = new File(fileName);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(f,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(taskJson);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布task
     * @param taskId
     */
    public void commitTask(String taskId){
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        String[] ss = taskId.split("^_^");
        String userId = ss[0];
        File fRead = new File(userId);
        File fWrite = new File(userId);
        try {
            FileReader fr = new FileReader(fRead);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                Task task = gson.fromJson(temp, Task.class);//反序列化
                if (!task.getId().equals(taskId)) {
                    reWrite.add(temp);
                } else {
                    task.setFlag1(true);
                    reWrite.add(gson.toJson(task));
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
        if (!fWrite.exists()) {
            try {
                fWrite.createNewFile();
                FileWriter fw = new FileWriter(fWrite);
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < reWrite.size(); i++) {
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

    public void modifyTask(String taskData){
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        Task t = gson.fromJson(taskData,Task.class);
        if(!t.isFlag()) {//未被接受
            String taskId = t.getId();
            String[] ss = taskId.split("^_^");
            String userId = ss[0];
            File fRead = new File(userId);
            File fWrite = new File(userId);
            try {
                FileReader fr = new FileReader(fRead);
                BufferedReader br = new BufferedReader(fr);
                String temp = "";
                while (null != (temp = br.readLine())) {
                    Task task = gson.fromJson(temp, Task.class);//反序列化
                    if (!task.getId().equals(taskId)) {
                        reWrite.add(temp);
                    } else {
                        reWrite.add(taskData);
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

            if (!fWrite.exists()) {
                try {
                    fWrite.createNewFile();
                    FileWriter fw = new FileWriter(fWrite);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (int i = 0; i < reWrite.size(); i++) {
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
    }

    /**
     * 通过taskId查找task
     * @param taskId
     * @return
     */
    public String findTask(String taskId){
        Gson gson = new Gson();
        String filename = taskId;
        String[] strings = filename.split("^_^");
        File f = new File(strings[0]);
        if(!f.exists()){
            try {
                f.createNewFile();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while(null != (temp = br.readLine())){
                Task t = gson.fromJson(temp,Task.class);
                if(t.getId().equals(taskId)){
                    return temp;
                }
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTask(String taskId){

            ArrayList<String> reWrite = new ArrayList<>();
            Gson gson = new Gson();
            String[] ss = taskId.split("^_^");
            String userId = ss[0];
            boolean b = false;
            File fRead = new File(userId);
            File fWrite = new File(userId);
            try {
                FileReader fr = new FileReader(fRead);
                BufferedReader br = new BufferedReader(fr);
                String temp = "";
                while (null != (temp = br.readLine())) {
                    Task task = gson.fromJson(temp, Task.class);//反序列化
                    if(!task.getId().equals(taskId)){
                        reWrite.add(temp);
                    }
                    else{
                        if(task.isFlag()){
                            b = false;
                            break;
                        }
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
    /**
     * 对文件的重新读写实现删除
     */
/*    public void deleteTasks(String taskId){

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
    }*/

    private String checkUserInfo(String userId){
        String out = "";
        String userFile = Project.class.getResource("/").getFile()+File.separator+"user.txt";
        File user = new File(userFile);
        Gson gson = new Gson();

        try {
            FileReader fr = new FileReader(user);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                UserInfo u = gson.fromJson(temp, UserInfo.class);//反序列化
                if(u.getName().equals(userId)){
                    out = temp;
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

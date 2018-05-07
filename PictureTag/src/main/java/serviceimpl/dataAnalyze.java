package serviceimpl;

import com.google.gson.Gson;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.io.*;
import java.util.ArrayList;

public class dataAnalyze {
    String sp = "&";
    String taskFileName ="";
    String committedTaskFile = dataAnalyze.class.getResource("/").getFile()+File.separator+"committedTask.txt";


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
        String[] strings = TaskId.split(sp);
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
        String userId = p.getId().split(sp)[0];

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
        String file = Project.class.getResource("/").getFile()+File.separator;
        Gson gson = new Gson();
        File f = new File(file+userId+".txt");
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
                String preTaskId = t.getId().split("&")[1];
                //System.out.println(preTaskId);
                int count = 0;
                for (int i = 0; i < preTaskId.length(); i++) {
                    char c = preTaskId.charAt(i);
                    if (c == '0') {
                        count++;
                    }
                }
                String nowId = preTaskId.substring(count);
                //System.out.println(nowId);
                int number = Integer.parseInt(String.valueOf(nowId));
                String id = String.valueOf(number + 1);
                //System.out.println(id);
                out = id;
                for (int i = 0; i < 5 - id.length(); i++) {
                    out = "0" + out;
                }
                //System.out.println(out);
                return userId+sp+out;
            }
            else{
                return userId+sp+"00001";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userId+sp+out;
    }

    /**
     * 领取任务
     * @param taskId
     * @param userId
     */
    public boolean acceptTask(String taskId,String userId){
        Gson g = new Gson();
        boolean b = false;
        String[] ss = taskId.split("&");
        String taskUserId = ss[0];
        //改变UserInfo
        if(!userId.equals(taskId)){
            userserviceImpl u = new userserviceImpl();
            UserInfo temp = u.getUser(userId);
            ArrayList<String> reTask = temp.getReceivepro();
            reTask.add(taskId);
            u.update(temp);
            b = true;
        }

        //改变taskUserInfo的task文件内的对应信息和committedTask里文件内的对应信息
        String taskUserFilePath = dataAnalyze.class.getResource("/").getFile()+File.separator+taskUserId+".txt";
        String taskData = findTask(taskId,taskUserFilePath);
        Task temp = g.fromJson(taskData,Task.class);
        temp.setFlag(true);
        modifyTask(g.toJson(temp),taskUserFilePath);
        modifyTask(g.toJson(temp),committedTaskFile);

        return b;
    }

    /**
     * 新建发布任务
     * @param taskJson
     */
    public void newTask(String taskJson){
        Gson gson = new Gson();
        Task t = gson.fromJson(taskJson,Task.class);
        t.setFlag1(false);
        t.setFlag(false);
        String filename = t.getId();
        String[] strings = filename.split(sp);
        String fileName = dataAnalyze.class.getResource("/").getFile()+File.separator+strings[0]+".txt";
        File f = new File(fileName);
        File f1 = new File(committedTaskFile);
        //System.out.println(f1.getAbsolutePath());
        if(!f1.exists()){
            try {
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(f,true);
            FileWriter fw1 = new FileWriter(f1,true);
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            bw.write(gson.toJson(t));
            bw.newLine();
            bw.close();
            fw.close();
            bw1.write(gson.toJson(t));
            bw1.newLine();
            bw1.close();
            fw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userId = strings[0];
        userserviceImpl u = new userserviceImpl();

        UserInfo user = u.getUser(userId);
        u.update(user);
        System.out.println(gson.toJson(user));
        user.setScore(user.getScore()-t.getSocre());
        ArrayList<String> temp = user.getLaunchpro();
        temp.add(t.getId());
        user.setLaunchpro(temp);

        //这里调用一下checkUserLevel的方法，返回修改后的userLevel
        user.setLevel(u.updateLevel(user.getScore()));
        u.update(user);
    }



    /**
     * 该方法要判断task是否完成，如果完成就将committedTask中的内容删掉，
     * @param taskId
     */
    public void completeTask(String taskId){
        String[] ss = taskId.split(sp);



    }

    /**
     * 发布task
     * @param taskId
     */
    public void commitTask(String taskId){
        String p = dataAnalyze.class.getResource("/").getFile()+File.separator;
        File committedFile = new File(p+"committedTask.txt");
        if(!committedFile.exists()){
            try {
                committedFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        String[] ss = taskId.split(sp);
        String userId = ss[0];
        File fRead = new File(p+userId+".txt");
        File fWrite = new File(p+userId+".txt");
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
                    FileWriter fw = new FileWriter(committedFile,true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(task.getId());
                    bw.newLine();
                    bw.close();
                    fw.close();
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

    /**
     * 修改task的信息
     * @param taskData
     * @return
     */
    public boolean modifyTask(String taskData,String filePath){
        boolean b = false;
        //String p = dataAnalyze.class.getResource("/").getFile()+File.separator;
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        Task t = gson.fromJson(taskData,Task.class);
        String taskId = t.getId();
        //String[] ss = taskId.split(sp);
        //String userId = ss[0];
        File fRead = new File(filePath);
        File fWrite = new File(filePath);
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
        return b;
    }

    /**
     * 通过taskId查找task
     * @param taskId
     * @return
     */
    public String findTask(String taskId,String filePath){
        Gson gson = new Gson();
        String filename = taskId;
        String[] strings = filename.split(sp);
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

    public ArrayList<String> receiveCommittedTaskIds(){
        ArrayList<String> out = new ArrayList<>();
        String p = dataAnalyze.class.getResource("/").getFile()+File.separator;
        File f = new File(p+"committedTask.txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())){
                out.add(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void deleteTask(String taskId,String filepath){
            //String p = dataAnalyze.class.getResource("/").getFile()+File.separator;
            ArrayList<String> reWrite = new ArrayList<>();
            Gson gson = new Gson();
            String[] ss = taskId.split(sp);
            String userId = ss[0];
            boolean b = false;
            File fRead = new File(filepath);
            File fWrite = new File(filepath);
            try {
                FileReader fr = new FileReader(fRead);
                BufferedReader br = new BufferedReader(fr);
                String temp = "";
                while (null != (temp = br.readLine())) {
                    Task task = gson.fromJson(temp, Task.class);//反序列化
                    if(!task.getId().equals(taskId)){
                        reWrite.add(temp);
                    }
//                    else{
//                        if(task.isFlag()){
//                            b = false;
//                            break;
//                        }
//                    }
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

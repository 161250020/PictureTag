package serviceimpl;

import com.google.gson.Gson;
import service.taskService;
import serviceimpl.tag.imageServiceImpl;
import serviceimpl.tagAccuracy.analyzeTagAccuracyImpl;
import serviceimpl.FindProjects;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class taskServiceImpl implements taskService {
    Gson gson = new Gson();
    FindProjects findProjects = new FindProjects();
    userserviceImpl userservice = new userserviceImpl();
    String sp = "_";
    String committedTaskFile = taskServiceImpl.class.getResource("/").getFile()+File.separator+"committedTask.task";
    String checkTaskFileName = analyzeTagAccuracyImpl.class.getResource("/").getFile()+ File.separator+"checkTask.task";

    public taskServiceImpl taskServiceImpl(){
        return new taskServiceImpl();
    }

    /**
     * 根据用户id获得用户的：积分奖励，群体排名，等级
     * @param userId
     */
    public String receiveUserDegree(String userId) {

        return checkUserInfo(userId);

    }

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

    /**
     *
     * 根据taskId获得taskInfo
     * @param taskId
     * @return
     */
    public String receiveTaskInfo(String taskId){

        Gson gson = new Gson();
        String[] strings = taskId.split(sp);
        String projectId = strings[0]+sp+strings[1];
        String path = taskServiceImpl.class.getResource("/").getFile()+File.separator;
        File f = new File(path+projectId+".task");
        //System.out.println(f.getAbsolutePath());
        String out = "";
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while(null != (temp = br.readLine())){
                Task p = gson.fromJson(temp,Task.class);
                if(p.getId().equals(taskId)){
                    out = temp;
                    //System.out.println(out);
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
     * 获得下一个taskId的方法
     * @param projectId
     * @return
     */
    public String getTaskId(String projectId){
        String file = Project.class.getResource("/").getFile()+File.separator;
        Gson gson = new Gson();
        File f = new File(file+projectId+".task");
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
                String preTaskId = t.getId().split(sp)[2];
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
                return projectId+sp+out;
            }
            else{
                return projectId+sp+"00001";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectId+sp+out;
    }

    /**
     * 领取任务-------需要User接口
     * @param userId
     */
    public boolean acceptTask(String taskId,String userId){
        Gson g = new Gson();
        boolean b = false;
        String[] ss = taskId.split(sp);
        String taskProjecgtId = ss[0]+sp+ss[1];
        //改变UserInfo
        if(!userId.equals(taskId)){
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");      //当前日期
            String d = format.format(date);
            //改变taskProjectInfo的task文件内的对应信息和committedTask里文件内的对应信息
            String taskUserFilePath = taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjecgtId+".task";
            String taskData = findTask(taskId,taskUserFilePath);
            Task t = g.fromJson(taskData,Task.class);
            t.setReceive(true);
            t.setReceiverId(userId);
            t.setReceiveDate(d);
            modifyTask(g.toJson(t),committedTaskFile);
            modifyTask(g.toJson(t),taskUserFilePath);
            //改变project文件的对应信息（以及user内的信息）
            findProjects.updateList(taskProjecgtId,userId,taskId);

            b = true;
        }
        else{
            System.out.println("ids are same");
        }
        return b;
    }

    /**
     * 新建发布任务,同时修改project和user信息u--------需要user接口
     * @param taskJson
     */
    public void newTask(String taskJson){
        Gson gson = new Gson();
        Task t = gson.fromJson(taskJson,Task.class);
        t.setPublish(true);
        t.setReceive(false);
        t.setSocre(Double.valueOf(t.getRequests().get(t.getRequests().size()-1)));
        //System.out.println(t.getSocre());
        String filename = t.getId();
        String[] strings = filename.split(sp);
        String projectId = strings[0]+sp+strings[1];
        String fileName = taskServiceImpl.class.getResource("/").getFile()+File.separator+projectId+".task";
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

        //向committed.task和checkTask.task文件中添加task纪录
        appendProjectTask(projectId,t);
        appendCommittedTask(t);


        //这里更新project信息
        findProjects.updateTaskId(projectId,t.getId());


        //更新user信息

/*        String userId = strings[0];
        userserviceImpl u = new userserviceImpl();

        UserInfo user = u.getUser(userId);
        u.update(user);
        //System.out.println(gson.toJson(user));
        double s = Double.valueOf(user.getScore()) - Double.valueOf(t.getSocre());
        //System.out.println(s);
        user.setScore(s);

        //这里调用一下checkUserLevel的方法，返回修改后的userLevel
        user.setLevel(u.updateLevel(user.getScore()));
        u.update(user);
        //System.out.println(gson.toJson(u.getUser(userId)));*/
    }

    public void gradeTask(String taskId, double grade){
        String[] ss = taskId.split(sp);
        String taskProjectId = ss[0]+sp+ss[1];
        Gson g = new Gson();
        //用户确认自己已经完成task，将task的complete属性修改为true
        String taskData = findTask(taskId,taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjectId+".task");
        Task temp = g.fromJson(taskData,Task.class);
        //System.out.println(grade);
        temp.setComplete(true);
        temp.setGrade(grade);
        modifyTask(g.toJson(temp),taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjectId+".task");
    }

    @Override
    public boolean confirmTask(String taskId, String userId,double grade) {
        boolean b =false;
        userserviceImpl u = new userserviceImpl();
        String[] ss = taskId.split(sp);
        String taskProjectId = ss[0]+sp+ss[1];
        //String s = findTask(taskId,taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjectId+".task");
        String s = findTask(taskId,checkTaskFileName);
        if(s == null){
            return b;
        }
        Task temp = gson.fromJson(s,Task.class);
        //System.out.println(temp.isComplete());

        //System.out.println(grade);
        //确认task完成，修改project信息
        findProjects.updateProgress(taskProjectId);
        //确认task完成，修改user信息
        if(grade >= 60){
            u.updatefinish(userId,taskId,true);
        }
        else{
            u.updatefinish(userId,taskId,false);
        }
        u.updateEvalu(userId,taskId,grade);
        u.updatescore(userId,gson.fromJson(receiveTaskInfo(taskId),Task.class).getSocre()*grade*1.0/100);
        //u.updatefinish(userId,taskId,true);
        //u.updateEvalu(userId,taskId,grade);
        //u.updatescore(userId,gson.fromJson(receiveTaskInfo(taskId),Task.class).getSocre()*grade*1.0/100);
        //删除committedTaskFile里的该task
        deleteTask(taskId,checkTaskFileName);
        //修改评分
        gradeTask(taskId,grade);
        b = true;

        return b;
    }

    /**
     * 完成是否有效交予confirmTask判断，如果有效就修改数据
     * 该方法要判断task是否完成，如果完成就将committedTask中的内容删掉---------需要user接口
     * @param taskId
     */
    public boolean completeTask(String taskId,String userId){
        boolean b = false;
        Gson g = new Gson();
        String[] ss = taskId.split(sp);
        String taskProjectId = ss[0]+sp+ss[1];
        String taskUserFilePath = taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjectId+".task";
        String taskData = findTask(taskId,taskUserFilePath);
        Task t = g.fromJson(taskData,Task.class);

        userserviceImpl u = new userserviceImpl();
        UserInfo userInfo = u.getUser(userId);
        //判断用户是否接受过该任务
        boolean flag = true;
        if(!flag){
                System.out.println("用户没有接受该任务！");
                return false;
        }
        //判断是否将所有图片标注完成

        if(!(t.getProgress() == t.getImageIds().size())){
            System.out.println("任务没有完成！");
            return false;
        }

        //将task内容添加到confirmTask.task，注意此时的task的bool值receive和publish都为true
        if(flag) {
            analyzeTagAccuracyImpl analyzeTagAccuracy = new analyzeTagAccuracyImpl();
            File c = new File(analyzeTagAccuracy.checkTaskFileName);
            if (!c.exists()) {
                try {
                    c.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                String temp = findTask(t.getId(),checkTaskFileName);
                if(temp == null){//如果未提交过
                    //System.out.println("未提交过");
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");      //当前日期
                    String d = format.format(date);
                    t.setFinishDate(d);
                    FileWriter fw = new FileWriter(c, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(gson.toJson(t));
                    Task ts=gson.fromJson(receiveTaskInfo(t.getId()),Task.class);
                    bw.newLine();
                    bw.close();
                    fw.close();
                }
                else{
                    modifyTask(taskData,checkTaskFileName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //用户确认自己已经完成task，将task的complete属性修改为true
            t.setComplete(true);
            modifyTask(g.toJson(t),taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjectId+".task");

            //删除committed中的信息
            deleteTask(taskId,committedTaskFile);
            b = true;
        }
        return b;
    }

    /**
     * 放弃任务
     * @param taskId
     * @param userId
     */
    public void giveUpTask(String taskId,String userId){

        //修改task属性
        rePublishTask(taskId);
        //修改user属性

    }

    /**
     * 根据tagType获得已发布但未被认领的task
      * @param tagType
     * @return
     */
    public ArrayList<String> receiveTasks(String tagType){

        Gson gson = new Gson();
        ArrayList<String> out = new ArrayList<>();
        File f = new File(committedTaskFile);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String t = "";
            while(null != (t = br.readLine())){
                Task temp = gson.fromJson(t,Task.class);
                if((temp.getTagType().equals(tagType))&&(!temp.isReceive())){
                    out.add(t);
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
     * 获得所有已发布并且未被complete的task的id
     * @return
     */
    public ArrayList<String> receiveCommittedTaskIds(){
        ArrayList<String> out = new ArrayList<>();
        File f = new File(committedTaskFile);
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

    /**
     * 如果task未被领取
     * @param taskData
     * @return
     */
    public boolean modifyTask(String taskData,String filePath){
        boolean b = false;
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        Task t = gson.fromJson(taskData,Task.class);
        String taskId = t.getId();
        File fRead = new File(filePath);
        if(!fRead.exists()){
            try {
                fRead.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File fWrite = new File(filePath);
        try {
            FileReader fr = new FileReader(fRead);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                Task task = gson.fromJson(temp, Task.class);//反序列化
                if ((!task.getId().equals(taskId))) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(fWrite);
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
        return b;
    }

    /**
     * 删除task的base方法
     * @param taskId
     * @param filepath
     */
    public boolean deleteTask(String taskId,String filepath){
            ArrayList<String> reWrite = new ArrayList<>();
            Gson gson = new Gson();
            String[] ss = taskId.split(sp);
            String userId = ss[0];
            boolean b = true;
            File fRead = new File(filepath);
            File fWrite = new File(filepath);
            try {
                FileReader fr = new FileReader(fRead);
                BufferedReader br = new BufferedReader(fr);
                String temp = "";
                while (null != (temp = br.readLine())) {
                    Task task = gson.fromJson(temp, Task.class);//反序列化
                    if((!task.getId().equals(taskId))){
                        reWrite.add(temp);
                    }
                    else{
                        b = false;
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        try {
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
        return b;
    }

    /**
     * 重新发布task，不存在额外判断
     * @param taskId
     */
    public void rePublishTask(String taskId){
        String[] ss = taskId.split(sp);
        String projectId = ss[0]+sp+ss[1];
        imageServiceImpl imageService = new imageServiceImpl();
        imageService.initializeTagData(taskId);//将对task图片的更改都消除,要修改committedTask.task和projectId.task文件内的内容
        //将对project的更改都消除
        String taskData = findTask(taskId,projectId+".task");
        //将任务重新加入committedTask.task文件，并删除在checkTask.task内的task
        if(null == findTask(taskId,committedTaskFile)){
            appendCommittedTask(gson.fromJson(taskData,Task.class));
        }
        deleteTask(taskId,checkTaskFileName);
    }

    /**
     * 通过taskId查找task,这个是功能方法
     * @param taskId
     * @return
     */
    public String findTask(String taskId,String filePath){
        Gson gson = new Gson();
        String filename = taskId;
        //String[] strings = filename.split(sp);
        File f = new File(filePath);
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

    /**
     * 添加task到committed.task文件，功能方法
     * @param task
     */
    private void appendCommittedTask(Task task){
        Gson g = new Gson();
        File f = new File(committedTaskFile);

        if(null == findTask(task.getId(),committedTaskFile)){
            try {
                FileWriter  fw = new FileWriter(f,true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(g.toJson(task,Task.class));
                bw.newLine();
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加task到指定的projectId.task文件，功能方法
     * @param projectId
     * @param task
     */
    private void appendProjectTask(String projectId,Task task){

        String proFileName = taskServiceImpl.class.getResource("/").getFile()+File.separator+projectId+".task";
        File f = new File(proFileName);
        try {
            FileWriter fw = new FileWriter(f,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(gson.toJson(task,Task.class));
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package serviceimpl;

import com.google.gson.Gson;
import service.taskService;
import vo.Project.Project;
import vo.Project.Task.Task;
import vo.Project.projectInfo;
import vo.UserInfo;

import java.io.*;
import java.util.ArrayList;

public class taskServiceImpl implements taskService {
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
            userserviceImpl u = new userserviceImpl();
            UserInfo temp = u.getUser(userId);
            ArrayList<String> reTask = temp.getReceivetask();
            reTask.add(taskId);
            u.update(temp);

            //改变taskProjectInfo的task文件内的对应信息和committedTask里文件内的对应信息
            String taskUserFilePath = taskServiceImpl.class.getResource("/").getFile()+File.separator+taskProjecgtId+".task";
            String taskData = findTask(taskId,taskUserFilePath);
            Task t = g.fromJson(taskData,Task.class);
            t.setReceive(true);            //System.out.println(g.toJson(t));
            //System.out.println(taskUserFilePath);
            modifyTask(g.toJson(t),committedTaskFile);
            modifyTask(g.toJson(t),taskUserFilePath);

            //改变project文件的对应信息
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
        System.out.println(t.getSocre());
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

        //这里更新project信息
        findProjects.updateTaskId(projectId,t.getId());


        //更新user信息
        String userId = strings[0];
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
        //System.out.println(gson.toJson(u.getUser(userId)));
    }

    public void gradeTask(String taskId, int grade){
        String[] ss = taskId.split(sp);
        String taskProjectId = ss[0]+sp+ss[1];
        Gson g = new Gson();
        //用户确认自己已经完成task，将task的complete属性修改为true
        String taskData = findTask(taskId,taskProjectId+".task");
        Task temp = g.fromJson(taskData,Task.class);
        temp.setComplete(true);
        modifyTask(g.toJson(temp),taskProjectId+".task");
    }

    @Override
    public void confirmTask(String taskId, String userId) {
        userserviceImpl u = new userserviceImpl();
        UserInfo userInfo = u.getUser(userId);
        String[] ss = taskId.split(sp);
        String taskProjectId = ss[0]+sp+ss[1];

        //确认task完成，修改project信息
        findProjects.updateProgress(taskProjectId);
        //确认task完成，修改user信息
        userservice.updatefinish(userId,taskId,true);
        //删除committedTaskFile里的该task
        deleteTask(taskId,checkTaskFileName);

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
        ArrayList<String> receivePro = userInfo.getReceivetask();
        //判断用户是否接受过该任务
        boolean flag = false;
        for (int i = 0; i < receivePro.size(); i++) {
            if(taskId.equals(receivePro.get(i))){
                flag = true;
            }
        }
        if(!flag){
                System.out.println("用户没有接受该任务！");
                return false;
        }
        //判断是否将所有图片标注完成
        if(!(t.getProgress() == t.getImageIds().size())){
            flag = false;
            System.out.println("任务没有完成！");
            return false;
        }
        //这里判断task是否超过期限

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
                FileWriter fw = new FileWriter(c, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(taskData);
                bw.newLine();
                bw.close();
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //用户确认自己已经完成task，将task的complete属性修改为true
        Task temp = g.fromJson(taskData,Task.class);
        temp.setComplete(true);
        modifyTask(g.toJson(temp),taskProjectId+".task");

        //删除committed中的信息
        deleteTask(taskId,committedTaskFile);

        return b;

        //用户接受过任务并完成标注，修改user信息，删除committed中的信息
        /*if((Integer)t.getProgress()==(Integer) t.getImageIds().size()) {
            int n = userInfo.getTaskNumber();
            double s =userInfo.getScore();
            userInfo.setTaskNumber(n + 1);
            userInfo.setScore(s+t.getSocre());
            userInfo.setLevel(u.updateLevel(userInfo.getScore()));
            u.update(userInfo);

            deleteTask(taskId,committedTaskFile);
        }
        else{
            b = false;
        }*/
        //修改project信息
        //findProjects.updateProgress(taskProjectId);
    }

    public void giveUpTask(String taskId,String userId){

        //修改task属性
        //修改user属性

    }

    /**
     * 修改task的信息
     * @param taskData
     * @return
     */
    public boolean modifyTask(String taskData,String filePath){
        boolean b = false;
        //String p = taskServiceImpl.class.getResource("/").getFile()+File.separator;
        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        Task t = gson.fromJson(taskData,Task.class);
        //System.out.println(gson.toJson(t));
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
                    //System.out.println(taskData);
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
     * 未被认领的task
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

    public void deleteTask(String taskId,String filepath){
            //String p = taskServiceImpl.class.getResource("/").getFile()+File.separator;
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
    }

    /**
     * 通过taskId查找task
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

    public void rePublishTask(String taskId){
        String[] ss = taskId.split(sp);
        //String projectId =

    }

}

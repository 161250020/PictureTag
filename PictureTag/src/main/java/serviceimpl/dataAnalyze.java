package serviceimpl;

import com.google.gson.Gson;
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
     * 对文件的重新读写实现删除
     * @param taskId
     */
    public void deleteTask(String taskId){

        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();

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

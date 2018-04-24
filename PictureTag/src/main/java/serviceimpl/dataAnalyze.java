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

    public ArrayList<String> receiveUserTask(String userId){

        Gson gson = new Gson();

        ArrayList<String> userInfos = checkUserInfo(userId);
        UserInfo userInfo = gson.fromJson(userInfos.get(0),UserInfo.class);
        ArrayList<String> receivepro = userInfo.getReceivepro();
        ArrayList<String> launchpro = userInfo.getLaunchpro();



        return null;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private  ArrayList<String> checkTask(String project){

        ArrayList<String> out = new ArrayList<>();
        int length = project.length();
        File task = new File(taskFileName);
        Gson gson = new Gson();

        try {
            FileReader fr = new FileReader(task);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                Task t = gson.fromJson(temp,Task.class);
                if(t.getId().substring(0,length-1).equals(project)){
                    out.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
        }

}

package stub;

import service.user;
import vo.UserInfo;

public class userstub implements user {

    public boolean login(String username,String password){

        return true;
    }
    public boolean register(String usename,String password,String name,String nickname){
        return true;
    }
    public boolean delete(String username){

        return true;
    }
    public  boolean update(UserInfo userInfo){
        return true;
    }
    public UserInfo getUser(String username){
        return new UserInfo();
    }
    public int updateLevel(double score){
        return 0;
    }
    public void updatereceiveTask(String username,String taskId){

    }
    public void updateEvalu(String username,String taskId,double score){

    }
    public void updatefinish(String username,String taskId,boolean finish){

    }
    public void updatescore(String username,double score){

    }
}

package Controller;

import service.user;
import serviceimpl.User.userserviceImpl;
import vo.UserInfo;

public class UserController implements user {
         userserviceImpl impl=new userserviceImpl();
       public boolean login(String username,String password){

           return impl.login(username,password);
        }
       public boolean register(String usename,String password,String name,String nickname){
           return impl.register(usename,password,name,nickname);
       }
       public boolean delete(String username){

           return impl.delete(username);
       }
       public  boolean update(UserInfo userInfo){
           return impl.update(userInfo);
       }
       public UserInfo getUser(String username){
           return impl.getUser(username);
       }
       public int updateLevel(double score){
           return impl.updateLevel(score);
       }                       //更新用户等级
       public void updatereceiveTask(String username,String taskId){
           impl.updatereceiveTask(username,taskId);
       }  //更新接受的任务
       public void updateEvalu(String username,String taskId,double score){
           impl.updateEvalu(username,taskId,score);
       }
       public void updatefinish(String username,String taskId,boolean finish){
           impl.updatefinish(username,taskId,finish);
       }
       public void updatescore(String username,double score){
           impl.updatescore(username,score);
       }
}

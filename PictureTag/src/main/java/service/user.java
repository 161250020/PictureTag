package service;

import vo.UserInfo;

public interface user {
         public boolean login(String username, String password);      //登陆
         public boolean register(String username, String password,String name,String nickname);   //注册
         public boolean delete(String username);                     //删除
         public boolean update(UserInfo userInfo);                   //更新用户
         public UserInfo getUser(String username);                    //根据id获得该用户
         public int updateLevel(double score);                       //更新用户等级
         public void updatereceiveTask(String username,String taskId);  //更新接受的任务
         public void updateEvalu(String username,String taskId,double score);
         public void updatefinish(String username,String taskId,boolean finish);
         public void updatescore(String username,double score);
}

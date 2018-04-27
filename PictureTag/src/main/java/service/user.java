package service;

import vo.UserInfo;

public interface user {
         public boolean login(String username, String password);      //登陆
         public boolean register(String username, String password);   //注册
         public boolean delete(String username);                     //删除
         public boolean update(String username, String password);      //修改
         public UserInfo getUser(String username);
}

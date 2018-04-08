package Controller;

import service.user;
import serviceimpl.userserviceImpl;
import vo.UserInfo;

public class UserController implements user {
         userserviceImpl impl=new userserviceImpl();
       public boolean login(String username,String password){
          return impl.login(username,password);
        }
       public boolean register(String usename,String password){
           return impl.register(usename,password);
       }
       public boolean delete(String username){
           return impl.delete(username);
       }
       public boolean update(String username,String password){return impl.update(username,password);}
       public UserInfo getUser(){return impl.getUser();}
}

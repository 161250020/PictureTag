package Controller;

import service.user;
import serviceimpl.userserviceImpl;
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
       public UserInfo getUser(String username){return impl.getUser(username);}
}

package serviceimpl.task;

import serviceimpl.userserviceImpl;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminUser {
          userserviceImpl impl=new userserviceImpl();
          public ArrayList<UserInfo> getAll(){
               return impl.getall();
          }
}

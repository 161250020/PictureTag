package serviceimpl.task;

import serviceimpl.userserviceImpl;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminUser {
          userserviceImpl impl=new userserviceImpl();
          public ArrayList<UserInfo> getAll() {
              ArrayList<UserInfo> list = impl.getall();
              ArrayList<UserInfo> newList = new ArrayList<UserInfo>();
              for (UserInfo user : list) {
                  if (!user.getUsername().equals("admin")) {
                      newList.add(user);
                  }
              }
              return newList;
          }
          public ArrayList<Integer> getUserCountByMonth(String month){
              ArrayList<Integer> count=new ArrayList<Integer>();

              return count;
          }
}

package serviceimpl.TestByMonitor;

import serviceimpl.User.AnalyzeUser;
import vo.UserInfo;

import java.util.ArrayList;

public class Main2 {
         public static void main(String args[]){
                AnalyzeUser user=new AnalyzeUser();
                ArrayList <UserInfo> list=user.calTurn();
                System.out.println(list.get(0).getUsername());
                System.out.println(user.getSelf_Turn("admin"));
         }
}

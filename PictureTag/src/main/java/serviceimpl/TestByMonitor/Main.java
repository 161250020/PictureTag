package serviceimpl.TestByMonitor;

import serviceimpl.User.userserviceImpl;
import vo.UserInfo;

public class Main {
    public static void main(String args[]){
        userserviceImpl impl=new userserviceImpl();
       if(impl.login("admin","admin")){
            System.out.println("ok");
        }
        //impl.register("gygy","199761");
       //impl.register("wlx","123456");
        //impl.register("admin","111");
        //impl.register("gygy","199761");
        //impl.delete("gygy");
        //impl.delete("7777");
        //impl.update("gygy","19991125");
        UserInfo user=new UserInfo("gygy","19991105","yiyi",1,null,null,0,1.1,null,null,"","","","");
        impl.update(user);
        System.out.println(impl.getUser("gygy").getLevel());
    }
}

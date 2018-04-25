package serviceimpl;

import vo.UserInfo;

public class Main {
    public static void main(String args[]){
        userserviceImpl impl=new userserviceImpl();
       /*if(impl.login("admin","admin")){
            System.out.println("ok");
        }
        impl.register("gygy","199761");*/
        impl.register("admin","111");
        impl.register("gygy","199761");
        impl.delete("gygy");
        impl.delete("7777");
        //impl.update("gygy","19991125");
    }
}

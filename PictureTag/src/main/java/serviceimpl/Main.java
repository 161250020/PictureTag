package serviceimpl;

public class Main {
    public static void main(String args[]){
        userserviceImpl impl=new userserviceImpl();
        impl.start();
        if(impl.login("admin","admin")){
            System.out.print("ok");
        }
    }
}

package stub;

import service.user;
import vo.UserInfo;

public class userstub implements user {

    public boolean login(String username,String password){
        return true;
    }
    public boolean register(String usename,String password,String nickname,String name){
        return true;
    }
    public boolean delete(String username){
        return true;
    }
    public boolean update(String username,String password){return true;}
    public UserInfo getUser(String username){return new UserInfo("admin","admin");}
}

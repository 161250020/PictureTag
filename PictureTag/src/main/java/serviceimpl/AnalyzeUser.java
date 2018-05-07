package serviceimpl;

import service.Analyze;
import vo.UserInfo;

import java.util.ArrayList;

public class AnalyzeUser implements Analyze {
    userserviceImpl impl=new userserviceImpl();
    public ArrayList<UserInfo> calTurn(){
          ArrayList<UserInfo> all=impl.getall();
          for(int i=0;i<all.size();i++){
                for(int j=0;j<all.size();j++){
                    if(all.get(i)!=null&&all.get(j)!=null&&all.get(i).getScore()>all.get(j).getScore()){
                           //System.out.println("111");
                           UserInfo temp=all.get(i);
                           all.set(i,all.get(j));
                           all.set(j,temp);
                    }
                }
          }
          return all;
    }
    public int getSelf_Turn(String username){
          int result=0;
          ArrayList<UserInfo> newUser=calTurn();
          for(int i=0;i<newUser.size();i++){
              if(newUser.get(i).getUsername().equals(username)&&newUser.get(i)!=null){
                  result=i+1;
              }
          }
          return result;
    }
}

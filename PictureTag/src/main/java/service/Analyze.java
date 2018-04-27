package service;

import vo.UserInfo;

import java.util.ArrayList;
public interface Analyze {
    public ArrayList<UserInfo> calTurn();       //获得全部排名
    public int getSelf_Turn(String username);  //获得个人排名
}
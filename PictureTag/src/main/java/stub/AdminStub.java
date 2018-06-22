package stub;

import service.Admin;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminStub implements Admin {
    public ArrayList<UserInfo> getAll(){
        return new ArrayList<UserInfo>();
    }
    public ArrayList<String> getUserCountByMonth(String month){
        return new ArrayList<String>();
    }
    public ArrayList<String> getUserCountByYear(String year){
        return new ArrayList<String>();
    }
    public ArrayList<String> getProjectCountByMonth(String month){
        return new ArrayList<String>();
    }
    public ArrayList<String> getProjectCountByYear(String year){
        return new ArrayList<String>();
    }
    public ArrayList<String> getTaskCountByLevel(){
        return new ArrayList<String>();
    }
    public ArrayList<String> getTaskCount(){
        return new ArrayList<String>();
    }
    public ArrayList<String> getScoreByLevel(){
        return new ArrayList<String>();
    }
    public ArrayList<String> getAllNorm(){
        return new ArrayList<String>();
    }
}

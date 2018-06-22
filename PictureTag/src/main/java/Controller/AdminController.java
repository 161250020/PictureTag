package Controller;

import service.Admin;
import serviceimpl.User.AdminUser;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminController implements Admin {
    AdminUser impl=new AdminUser();
    public ArrayList<UserInfo> getAll(){

        return impl.getAll();
    }
    public ArrayList<String> getUserCountByMonth(String month){

        return impl.getUserCountByMonth(month);
    }
    public ArrayList<String> getUserCountByYear(String year){

        return impl.getUserCountByYear(year);
    }
    public ArrayList<String> getProjectCountByMonth(String month){

        return impl.getProjectCountByMonth(month);
    }
    public ArrayList<String> getProjectCountByYear(String year){

        return impl.getProjectCountByYear(year);
    }
    public ArrayList<String> getTaskCountByLevel(){

        return impl.getTaskCountByLevel();
    }
    public ArrayList<String> getTaskCount(){

        return impl.getTaskCount();
    }
    public ArrayList<String> getScoreByLevel(){

        return impl.getScoreByLevel();
    }
    public ArrayList<String> getAllNorm(){

        return impl.getAllNorm();
    }
}

package service;

import vo.UserInfo;

import java.util.ArrayList;

public interface Admin {
    public ArrayList<UserInfo> getAll();
    public ArrayList<String> getUserCountByMonth(String month);
    public ArrayList<String> getUserCountByYear(String year);
    public ArrayList<String> getProjectCountByMonth(String month);
    public ArrayList<String> getProjectCountByYear(String year);
    public ArrayList<String> getTaskCountByLevel();
    public ArrayList<String> getTaskCount();
    public ArrayList<String> getScoreByLevel();
    public ArrayList<String> getAllNorm();
}
package service;

import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;
public interface AnalyzeUser {
    public ArrayList<UserInfo> calTurn();       //获得全部排名
    public int getSelf_Turn(String username);  //获得个人排名
    public void updateEvalu(String username,String taskId,double score);
    public double calEffiency(String username);
    public boolean alarm(String username);
    public String recommend(String username);
    public ArrayList<Task> recom(String username);
    public double calTruth(String username);
    public double correlation(String username);
    public double ExpectedScore(String username);
    public ArrayList<Double> relationbyScoreandEvalu(String username);
    public double SupportbyScoreandEvalu(String username);
    public double ConfidencebyScoreandEvalu(String username);
    public double LiftbyScoreandEvalu(String username);
}
package Controller;

import service.AnalyzeUser;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;

public class AnalyzeUserController implements AnalyzeUser{
    serviceimpl.User.AnalyzeUser impl=new serviceimpl.User.AnalyzeUser();
    public ArrayList<UserInfo> calTurn(){

        return impl.calTurn();
    }
    public int getSelf_Turn(String username){

        return impl.getSelf_Turn(username);
    }
    public void updateEvalu(String username,String taskId,double score){

        impl.updateEvalu(username,taskId,score);
    }
    public double calEffiency(String username){

        return impl.calEffiency(username);
    }
    public boolean alarm(String username){

        return impl.alarm(username);
    }
    public String recommend(String username){

        return impl.recommend(username);
    }
    public ArrayList<Task> recom(String username){

        return impl.recom(username);
    }
    public double calTruth(String username){

        return impl.calTruth(username);
    }
    public double correlation(String username){

        return impl.correlation(username);
    }
    public double ExpectedScore(String username){

        return impl.ExpectedScore(username);
    }
    public ArrayList<Double> relationbyScoreandEvalu(String username){

        return impl.relationbyScoreandEvalu(username);
    }
    public double SupportbyScoreandEvalu(String username){

        return impl.SupportbyScoreandEvalu(username);
    }
    public double ConfidencebyScoreandEvalu(String username){

        return impl.ConfidencebyScoreandEvalu(username);
    }
    public double LiftbyScoreandEvalu(String username){

        return impl.LiftbyScoreandEvalu(username);
    }
}

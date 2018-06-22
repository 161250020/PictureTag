package stub;

import service.AnalyzeUser;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;

public class AnalyzeUserstub implements AnalyzeUser{
    public ArrayList<UserInfo> calTurn(){

        return new ArrayList<UserInfo>();
    }
    public int getSelf_Turn(String username){

        return 1;
    }
    public void updateEvalu(String username,String taskId,double score){

    }
    public double calEffiency(String username){

        return 0;
    }
    public boolean alarm(String username){
        return false;
    }
    public String recommend(String username){

        return "all";
    }
    public ArrayList<Task> recom(String username){

        return new ArrayList<Task>();
    }
    public double calTruth(String username){
        return 0;
    }
    public double correlation(String username){

        return 0;
    }
    public double ExpectedScore(String username){

        return 0.0;
    }
    public ArrayList<Double> relationbyScoreandEvalu(String username){

        return new ArrayList<Double>();
    }
    public double SupportbyScoreandEvalu(String username){

        return 0.0;
    }
    public double ConfidencebyScoreandEvalu(String username){
        return 0.0;
    }
    public double LiftbyScoreandEvalu(String username){
        return 0.0;
    }

}

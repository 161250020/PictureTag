package serviceimpl.task;

import com.google.gson.Gson;
import serviceimpl.FindProjects;
import serviceimpl.tagAccuracy.analyzeTagAccuracyImpl;
import serviceimpl.taskServiceImpl;
import serviceimpl.userserviceImpl;
import vo.Project.Task.Task;

import java.io.*;
import java.util.ArrayList;


public class taskFilterServiceImpl {

    Gson gson = new Gson();
    FindProjects findProjects = new FindProjects();
    userserviceImpl userservice = new userserviceImpl();
    String sp = "_";
    String committedTaskFile = taskServiceImpl.class.getResource("/").getFile()+ File.separator+"committedTask.task";
    String checkTaskFileName = analyzeTagAccuracyImpl.class.getResource("/").getFile()+ File.separator+"checkTask.task";

    public ArrayList<String> findTaskByDate(String projectId, String startDate, String endDate){
        dateComparer dateComparer = new dateComparer();
        String startTemp = "";
        String endTemp = "";
        String projectIdTemp = "";
        ArrayList<String> out = new ArrayList<>();
        File f = new File(committedTaskFile);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            Task t = null;
            String temp = "";
            while ((temp = br.readLine()) != null){
                t = gson.fromJson(temp,Task.class);
                startTemp = t.getStartDate();
                endTemp = t.getEndDate();
                String[] ss = t.getId().split(sp);
                projectIdTemp = ss[0]+sp+ss[1];
                if(dateComparer.isAfter(startTemp,startDate) && dateComparer.isBefore(endTemp,endDate) && projectIdTemp.equals(projectId)){
                    out.add(temp);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

}

package serviceimpl;

import com.google.gson.Gson;
import javafx.util.converter.DateStringConverter;
import vo.Project.Task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;


public class taskFilterServiceImpl {

    Gson gson = new Gson();
    FindProjects findProjects = new FindProjects();
    userserviceImpl userservice = new userserviceImpl();
    String sp = "_";
    String committedTaskFile = taskServiceImpl.class.getResource("/").getFile()+ File.separator+"committedTask.task";
    String checkTaskFileName = analyzeTagAccuracyImpl.class.getResource("/").getFile()+ File.separator+"checkTask.task";

    public ArrayList<String> findTaskByDate(String taskId, String startDate, String endDate,String tagType){
        Date startTemp = null;
        Date endTemp = null;
        Date start = changeToDate(startDate);
        Date end = changeToDate(endDate);
        ArrayList<String> out = new ArrayList<>();
        File f = new File(committedTaskFile);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            Task t = null;
            String temp = "";
            while ((temp=br.readLine())!=null){
                t = gson.fromJson(temp,Task.class);
                startTemp = changeToDate(t.getStartDate());
                endTemp = changeToDate(t.getEndDate());
                if(startTemp.after(start)&&endTemp.before(end)){
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

    //将类似于20180607的字符串转为Date类型便于比较
    private Date changeToDate(String s){
        Date d = null;
        String date = s.substring(0,4)+"-"+s.substring(4,6)+"-"+s.substring(6,s.length());
        DateStringConverter dateStringConverter = new DateStringConverter();
        d = dateStringConverter.fromString(date);
        return d;
    }

}

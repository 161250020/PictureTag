package serviceimpl.Bill;

import com.google.gson.Gson;
import serviceimpl.taskServiceImpl;
import serviceimpl.userserviceImpl;
import vo.Project.Task.Task;
import vo.UserInfo;

import java.util.ArrayList;

public class BillServiceImpl {
      private userserviceImpl impl=new userserviceImpl();
      private taskServiceImpl service=new taskServiceImpl();
      private Gson gson=new Gson();
      public ArrayList<String> BillCounts(String username){
          ArrayList<String> result=new ArrayList<String>();
          ArrayList<Task> tasks=hh(username);
          for(Task task:tasks){
              result.add(""+(task.getSocre()*task.getGrade()/100.0));
          }
          return result;
      }
      public ArrayList<String> BillTasks(String username){
          ArrayList<String> taskIds=new ArrayList<String>();
          UserInfo user=impl.getUser(username);
          for(String str:user.getReceiveEvalu().keySet()){
              taskIds.add(str);
          }
          return taskIds;
      }

      //辅助方法
      public ArrayList<Task> hh(String username){
        ArrayList<Task> tasks=new ArrayList<Task>();
        UserInfo user=impl.getUser(username);
        for(String str:user.getReceiveEvalu().keySet()){
            tasks.add(gson.fromJson(service.receiveTaskInfo(str),Task.class));
         }
        return tasks;
      }
   }

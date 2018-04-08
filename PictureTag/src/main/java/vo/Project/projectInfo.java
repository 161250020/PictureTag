package vo.Project;

import java.util.ArrayList;

public class projectInfo {         //所有要完成的任务id


    ArrayList<String> taskIds;

    public projectInfo(){
        taskIds  = new ArrayList<String>();
    }

    public ArrayList<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(ArrayList<String> taskIds) {
        this.taskIds = taskIds;
    }
}

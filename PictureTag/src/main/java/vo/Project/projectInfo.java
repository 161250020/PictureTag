package vo.Project;

import java.util.ArrayList;

public class projectInfo {         //所有要完成的任务id

    String projectId;
    ArrayList<String> taskIds;

    public projectInfo(){
        this.projectId=null;
        taskIds  = new ArrayList<String>();
    }

    public ArrayList<String> getTaskIds() {

        return taskIds;
    }

    public void setTaskIds(ArrayList<String> taskIds) {

        this.taskIds = taskIds;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}

package serviceimpl;

import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import service.imageService;
import vo.Project.Task.Task;
import vo.Project.Task.image;

public class imageServiceImpl implements imageService {
    Gson gson = new Gson();
    String imageFileName = "_images.txt";
    String sp = "_";
    String committedTaskFile = taskServiceImpl.class.getResource("/").getFile()+File.separator+"committedTask.task";
/*    public imageServiceImpl imageServiceImpl(){
        return new imageServiceImpl();
    }*/

    public String receiveImgId(String taskId){
        String out = "";
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator;
        Gson gson = new Gson();
        String[] ss = taskId.split(sp);
        String projectId = ss[0]+sp+ss[1];
        File f = new File(filePath+taskId+imageFileName);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = 0;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())){
                count++;
            }
            //System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(count >= 99999){
            return "00000";
        }
        else{
            out = Integer.toString(count);
            //System.out.println(out.length());
            for (int i = 0; i < 9-out.length(); i++) {
                out = "0" + out;
            }
        }

        out = taskId + sp + out;

        return out;
    }

    public String writeTag(String jsonData){
        //System.out.println(tagPath);
        //System.out.println("call again");
        String taskId="";
        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        String[] strings =i.getId().split(sp);
        taskId = strings[0]+sp+strings[1];
        System.out.println(taskId);
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;
        File file = new File(filePath);
        //System.out.println(file.getAbsolutePath());
       try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgId = receiveImgId(taskId);
        i.setId(imgId);
        try {
            if(!"".equals(jsonData)) {
                FileWriter fileWriter = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(gson.toJson(i));
                bw.newLine();
                bw.close();
                fileWriter.close();
                //System.out.println(jsonData);
            }
            else{
                System.out.println("空值");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(imgId);
        return imgId;
    }

    public String receiveTag(String imageId){
        //this.filePath = filePath;
        Gson gson = new Gson();
        String[] strings =imageId.split(sp);
        String taskId = strings[0]+sp+strings[1];
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;
        String out = "";
        //按行读取文件内容
        File file = new File(filePath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String temp="";
            while(null != (temp = br.readLine()) ){
                image tempImg = gson.fromJson(temp,image.class);
                if(imageId.equals(tempImg.getId())) {
                    out = temp;
                    break;
                }
            }
            br.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(out);
        return out;
    }

    public void modifyTag(String jsonData) {

        taskServiceImpl d = new taskServiceImpl();
        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        String[] ss = i.getId().split(sp);
        String taskId = ss[0]+sp+ss[1];
        ArrayList<String> reWrite = new ArrayList<>();
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;

        image im = gson.fromJson(jsonData,image.class);
        im.setFlag(true);
        int count = 1;//数完成图片的数量

        File fRead = new File(filePath);
        File fWrite = new File(filePath);
        try {
            FileReader fr = new FileReader(fRead);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())){
                image iTemp =gson.fromJson(temp,image.class);
                System.out.println(im.getId());
                System.out.println(iTemp.getId());
                if(im.getId().equals(iTemp.getId())){
                    reWrite.add(gson.toJson(im));
                }
                else{
                    if(true == iTemp.isFlag()){
                        count++;
                    }
                    reWrite.add(temp);
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fRead.delete();

        if(!fWrite.exists()){
            try {
                fWrite.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(fWrite);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int j = 0; j < reWrite.size(); j++) {
                bw.write(reWrite.get(j));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改task的值
        String taskUserFilePath = taskServiceImpl.class.getResource("/").getFile()+File.separator+ss[0]+sp+ss[1]+".task";
        String taskData = d.findTask(taskId,taskUserFilePath);
        Task t = gson.fromJson(taskData,Task.class);
        t.setReceive(true);
        t.setProgress(count);
        System.out.println(t.getId());
        d.modifyTask(gson.toJson(t),taskUserFilePath);
        d.modifyTask(gson.toJson(t),committedTaskFile);

    }

    public void initializeTagData(String taskId){//将该task的标注信息全部删除，包括committedTask.task和checkTask.task
        taskServiceImpl taskService = new taskServiceImpl();
        String[] ss = taskId.split(sp);
        String projectId = ss[0]+sp+ss[1];
        String fileName = projectId+".task";
        String taskData = "";
        String changedTaskData = "";
        taskData = taskService.findTask(taskId,committedTaskFile);
        if(taskData != null){
            String temp = initializeTask(taskData);
            taskService.modifyTask(temp,committedTaskFile);
        }
        taskData = taskService.findTask(taskId,fileName);
        if(taskData != null){
            String temp = initializeTask(taskData);
            taskService.modifyTask(temp,committedTaskFile);
        }
    }

    public String initializeTask(String taskData){
        String output = "";

        Task temp = gson.fromJson(taskData,Task.class);
        Task out = new Task();
        out.setId(temp.getId());
        out.setName(temp.getName());
        out.setTagType(temp.getTagType());
        out.setSocre(temp.getSocre());
        out.setProgress(0);
        out.setReceive(false);
        out.setPublish(true);
        out.setComplete(false);
        out.setGrade(-1);

        output = gson.toJson(out);
        return output;
    }

}

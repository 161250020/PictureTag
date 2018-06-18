package serviceimpl.tag;

import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import service.imageService;
import serviceimpl.taskServiceImpl;
import vo.Project.Task.Task;
import vo.Project.Task.image;

public class imageServiceImpl implements imageService {
    Gson gson = new Gson();
    String imageFileName = "_images.txt";
    String sp = "_";
    String committedTaskFile = taskServiceImpl.class.getResource("/").getFile()+File.separator+"committedTask.task";

/*    public String receiveImgId(String taskId,String filePath){
        String out = "";
        File f = new File(filePath);
        //System.out.println(f.getAbsolutePath());
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
                //System.out.println(temp);
                count++;
            }
            //System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("count:"+count);
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
    }*/

    public String saveTag(String imageData){
        //System.out.println(tagPath);
        //System.out.println("call again");
        ArrayList<String> imgIds = new ArrayList<>();
        String taskId="";
        Gson gson = new Gson();
        image[] images = gson.fromJson(imageData,image[].class);
        int length = images.length;
        //System.out.println(i.getId());
        String[] strings =images[0].getId().split(sp);
        taskId = strings[0]+sp+strings[1]+sp+strings[2];
        //System.out.println(taskId);
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;
        File file = new File(filePath);
        //System.out.println(file.getAbsolutePath());
       try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgId = "";
        try {
            if(!"".equals(imageData)) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                for (int i = 0; i < length; i++) {
                    String number = String.valueOf(i);
                    imgId = number;
                    for (int j = 0; j < 5-number.length(); j++) {
                        imgId = "0" + imgId;
                    }
                    imgId = taskId+sp+imgId;
                    imgIds.add(imgId);
                    images[i].setId(imgId);
                    bw.write(gson.toJson((images[i])));
                    bw.newLine();
                }
                bw.close();
                fileWriter.close();
                //System.out.println(jsonData);
                //System.out.println("----------------------------------");
                //System.out.println(imgId);
                //System.out.println("----------------------------------");
            }
            else{
                System.out.println("空值");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(imgId);
        return gson.toJson(imgIds);
    }

    public String receiveTag(String imageId){
        //this.filePath = filePath;
        Gson gson = new Gson();
        String[] strings =imageId.split(sp);
        String taskId = strings[0]+sp+strings[1]+sp+strings[2];
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
        //System.out.println(out);
        return out;
    }

    public void modifyTag(String jsonData) {

        taskServiceImpl d = new taskServiceImpl();
        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        String[] ss = i.getId().split(sp);
        String taskId = ss[0]+sp+ss[1]+sp+ss[2];
        ArrayList<String> reWrite = new ArrayList<>();
        String filePath = imageServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;

        image im = gson.fromJson(jsonData,image.class);
        //System.out.println(im.getSentences());
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
                //System.out.println(im.getId());
                //System.out.println(iTemp.getId());
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
        //System.out.println(t.getId());
        d.modifyTask(gson.toJson(t),taskUserFilePath);
        d.modifyTask(gson.toJson(t),committedTaskFile);

    }

    /**
     * 格式化task内有关图片的标注信息和图片文件的标注信息
     * @param taskId
     */
    public void initializeTagData(String taskId){
        boolean b = true;
        taskServiceImpl taskService = new taskServiceImpl();
        String[] ss = taskId.split(sp);
        String projectId = ss[0]+sp+ss[1];
        String projectfileName = taskServiceImpl.class.getResource("/").getFile()+File.separator+projectId+".task";
        String imageFilePath = taskServiceImpl.class.getResource("/").getFile()+File.separator+taskId+imageFileName;
        String taskData = "";
        taskData = taskService.findTask(taskId,committedTaskFile);
        if(taskData != null){
            String temp = initializeTask(taskData);
            taskService.modifyTask(temp,committedTaskFile);
            b = false;
        }

        taskData = taskService.findTask(taskId,projectfileName);
        if(taskData != null){
            String temp = initializeTask(taskData);
            taskService.modifyTask(temp,projectfileName);
            b = false;
        }
        else{
            b = true;
        }

        if(!b){
            initializeImgs(imageFilePath);
        }

    }

    /**
     * 将目标task的所有图片的标注信息全部还原
     * @param filePath
     */
    public void initializeImgs(String filePath){

        ArrayList<String> reWrite = new ArrayList<>();
        Gson gson = new Gson();
        image tempImage = null;
        File fRead = new File(filePath);
        File fWrite = new File(filePath);
        try {
            FileReader fr = new FileReader(fRead);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())) {
                tempImage = gson.fromJson(temp,image.class);
                tempImage.setFlag(false);
                tempImage.setUrl(tempImage.getFilename());
                reWrite.add(gson.toJson(tempImage));
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fRead.delete();

        if (!fWrite.exists()) {
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
            for (int i = 0; i < reWrite.size(); i++) {
                bw.write(reWrite.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 格式化task
     * @param taskData
     * @return
     */
    public String initializeTask(String taskData){
        String output = "";

        Task temp = gson.fromJson(taskData,Task.class);
        Task out = new Task();

        out.setId(temp.getId());
        out.setName(temp.getName());
        out.setTagType(temp.getTagType());
        out.setSocre(temp.getSocre());
        out.setProgress(0);
        out.setStartDate(temp.getStartDate());
        out.setEndDate(temp.getEndDate());
        out.setImageIds(temp.getImageIds());
        out.setRequests(temp.getRequests());
        out.setReceive(false);
        out.setPublish(true);
        out.setComplete(false);
        out.setGrade(-1);

        output = gson.toJson(out);

        return output;
    }

}

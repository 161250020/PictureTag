package serviceimpl;

import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import service.imageService;
import vo.Project.Task.Task;
import vo.Project.Task.image;

public class tagIO implements imageService {
    String sp = "&";
    String committedTaskFile = dataAnalyze.class.getResource("/").getFile()+File.separator+"committedTask.txt";
    //String filePath = "C://TomCat//apache-tomcat-8.5.29//apache-tomcat-8.5.29//webapps//TagFile//tag.txt";
    //URL tagPath = Thread.currentThread().getContextClassLoader().getResource("");
    //String filePath = "C:\\TomCat\\apache-tomcat-8.5.29\\apache-tomcat-8.5.29\\webapps\\PictureTag\\TagFile\\tagFile.txt";
    public tagIO(){

    }

    public String receiveImgId(String TaskId){
        String out = "";
        String filePath = tagIO.class.getResource("/").getFile()+File.separator;
        Gson gson = new Gson();
        String userId = TaskId.split(sp)[0];
        File f = new File(filePath+TaskId+"&Imgs.txt");
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

        out = TaskId + sp + out;

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
        //System.out.println(taskId);
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+taskId+"&Imgs.txt";
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
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+strings[0]+"&"+strings[1]+"&Imgs.txt";
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

        return out;
    }

    public void modifyTag(String jsonData) {
        dataAnalyze d = new dataAnalyze();
        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        String[] ss = i.getId().split(sp);
        String taskId = ss[0]+"&"+ss[1];
        ArrayList<String> reWrite = new ArrayList<>();
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+taskId+"&Imgs.txt";

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
                FileWriter fw = new FileWriter(fWrite);
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
        }

        //修改task的值
        String taskUserFilePath = dataAnalyze.class.getResource("/").getFile()+File.separator+taskId+".txt";
        String taskData = d.findTask(taskId,taskUserFilePath);
        Task t = gson.fromJson(taskData,Task.class);
        t.setFlag(true);
        d.modifyTask(gson.toJson(t),taskUserFilePath);
        d.modifyTask(gson.toJson(t),committedTaskFile);

    }
}

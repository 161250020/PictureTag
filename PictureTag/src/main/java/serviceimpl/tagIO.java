package serviceimpl;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import service.imageService;
import util.*;
import vo.Project.Task.image;

public class tagIO implements imageService {
    //String filePath = "C://TomCat//apache-tomcat-8.5.29//apache-tomcat-8.5.29//webapps//TagFile//tag.txt";
    URL tagPath = Thread.currentThread().getContextClassLoader().getResource("");
    //String filePath = "C:\\TomCat\\apache-tomcat-8.5.29\\apache-tomcat-8.5.29\\webapps\\PictureTag\\TagFile\\tagFile.txt";
    String rootPath = tagIO.class.getClassLoader().getResource("/").getPath();
    String filePath = rootPath+"\\tagFile.txt";
    public tagIO(){

    }

    public boolean writeTag(String jsonData){
        //this.filePath = filePath;
        //System.out.println(tagPath);
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
      /*  try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            if(!"".equals(jsonData)) {
                FileWriter fileWriter = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(jsonData);
                bw.newLine();
                bw.close();
                fileWriter.close();
                System.out.println(jsonData);
                return true;
            }
            else{
               return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> receiveTag(String s){
        //this.filePath = filePath;
        ArrayList<String> out = new ArrayList<String>();
        ArrayList<String> imgsStr = new ArrayList<String>();
        ArrayList<image> images = new ArrayList<image>();
        //按行读取文件内容
        File file = new File(this.filePath);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String temp="";
            while(null != (temp = br.readLine()) ){
                imgsStr.add(temp);
            }
            br.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将字符串转化为对应的image对象
        for (int i = 0; i < imgsStr.size(); i++) {
            images.add(FindTag.getBean(imgsStr.get(i)));   //修改
       }
       //查找filename为s的image对象RevertJsonObject
        //FindTag
        //FindProjects
        //FileReadandWrite
        for (int i = 0; i < images.size(); i++) {
            if(s.equals(images.get(i).getUrl())){
                out.add(imgsStr.get(i));
            }
        }
        return out;
    }

    public boolean modifyTag(String jsonData) {
        return false;
    }


}

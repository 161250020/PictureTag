package serviceimpl;

import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import service.imageService;
import vo.Project.Task.image;

public class tagIO implements imageService {
    //String filePath = "C://TomCat//apache-tomcat-8.5.29//apache-tomcat-8.5.29//webapps//TagFile//tag.txt";
    //URL tagPath = Thread.currentThread().getContextClassLoader().getResource("");
    //String filePath = "C:\\TomCat\\apache-tomcat-8.5.29\\apache-tomcat-8.5.29\\webapps\\PictureTag\\TagFile\\tagFile.txt";
    public tagIO(){

    }

    public String receiveImgId(String TaskId){
        String out = "";
        String filePath = tagIO.class.getResource("/").getFile()+File.separator;
        Gson gson = new Gson();
        String userId = TaskId.split("^_^")[0];
        File f = new File(filePath+userId+"Imgs.txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = 1;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            while (null != (temp = br.readLine())){
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(count>=99999){
            return "00000";
        }
        else{
            out = Integer.toString(count);
            for (int i = 0; i < 5-out.length(); i++) {
                out = "0" + out;
            }
        }

        out = TaskId + "^_^" + out;

        return out;
    }

    public boolean writeTag(String jsonData){
        //this.filePath = filePath;
        //System.out.println(tagPath);
        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        String[] strings =i.getId().split("^_^");
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+strings[0]+"Imgs.txt";
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

    public String receiveTag(String imageId){
        //this.filePath = filePath;
        Gson gson = new Gson();
        String[] strings =imageId.split("^_^");
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+strings[0]+"Imgs.txt";
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

        Gson gson = new Gson();
        image i = gson.fromJson(jsonData,image.class);
        ArrayList<String> reWrite = new ArrayList<>();
        String filePath = tagIO.class.getResource("/").getFile()+File.separator+i.getId().split("^_^")[0]+"Imgs.txt";

        image im = gson.fromJson(jsonData,image.class);
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
    }


}

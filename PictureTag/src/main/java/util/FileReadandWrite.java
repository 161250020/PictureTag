package util;

import java.io.*;
import java.util.ArrayList;


public class FileReadandWrite {
    public static ArrayList<String> ReadFile(String path){
        ArrayList<String> content=new ArrayList<String>();
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String str="";
            while((str=bufferedReader.readLine())!=null){
                content.add(str);
            }
            bufferedReader.close();
            //System.out.println("Read");
        }catch(IOException e){
            e.printStackTrace();
        }
        return content;
    }
    public static void WriteFile(String path,String content){
        try{
            File file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer=new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(content);
            bw.newLine();
            bw.close();
            writer.close();
            //System.out.println("hello");    //测试
        }catch(IOException E){
            E.printStackTrace();
        }
    }
}

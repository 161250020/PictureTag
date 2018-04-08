package util;

import java.io.*;
import java.util.ArrayList;

public class FileReadandWrite {
    public static ArrayList<String> ReadFile(String path){
        ArrayList<String> content=new ArrayList<String>();
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String str="";
            while((str=bufferedReader.readLine())!=null){
                content.add(str);
            }
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return content;
    }
    public static void WriteFile(String path,String content){
        try{
            File file=new File(path);
            FileWriter writer=new FileWriter(file,true);
            writer.write(content);
            writer.close();
        }catch(IOException E){
            E.printStackTrace();
        }
    }
}

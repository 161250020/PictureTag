package util;

import vo.Project.Task.Sentence;
import vo.Project.Task.image;

import java.util.List;

public class FindTag {

    public static final image getBean(String jsonStr){
        image image = new image();
        int imgid=0;
        String id="";
        String url="";
        String filename="";
        String split="";
        List<Integer> sentids = null;
        List<Sentence> sentences  = null;

        String[] strs = jsonStr.split("\"");
        id = strs[strs.length-2];
        image.setUrl(id);//返回的image只存在
         return image;
    }

}

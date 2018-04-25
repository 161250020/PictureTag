package stub;

import service.imageService;

import java.util.ArrayList;

public class imageservicestub implements service.imageService {           //新版本缺少路径,不知道是什么意思
    public boolean writeTag(String jsonData){
        return true;
    }

    public ArrayList<String> receiveTag(String s){
        return new ArrayList<String>();
    }

    public void modifyTag(String jsonData){
    }
}

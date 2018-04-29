package stub;

import java.util.ArrayList;

public class imageservicestub implements service.imageService {           //新版本缺少路径,不知道是什么意思
    public boolean writeTag(String jsonData){
        return true;
    }

    public String receiveTag(String s){
        return null;
    }

    public void modifyTag(String jsonData){
    }
}

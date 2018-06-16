package serviceimpl.tagAccuracy;

import service.analyzeTagAccuracyService;
import serviceimpl.tag.imageServiceImpl;

import java.io.File;

public class analyzeTagAccuracyImpl implements analyzeTagAccuracyService {

    serviceimpl.tag.imageServiceImpl imageServiceImpl = new imageServiceImpl();
    String checkTaskFileName = analyzeTagAccuracyImpl.class.getResource("/").getFile()+ File.separator+"checkTask.task";

    public void checkTagAccuracy(boolean b){
        if(true == b){

        }
        else{

        }
    }

}

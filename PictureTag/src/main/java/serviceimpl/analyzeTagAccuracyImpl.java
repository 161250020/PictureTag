package serviceimpl;

import service.analyzeTagAccuracyService;

import java.io.File;

public class analyzeTagAccuracyImpl implements analyzeTagAccuracyService {

    imageServiceImpl imageServiceImpl = new imageServiceImpl();
    String checkTaskFileName = analyzeTagAccuracyImpl.class.getResource("/").getFile()+ File.separator+"checkTask.task";

    public void checkTagAccuracy(boolean b){
        if(true == b){

        }
        else{

        }
    }

}

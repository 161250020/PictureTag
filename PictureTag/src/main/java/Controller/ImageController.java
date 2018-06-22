package Controller;

import service.imageService;
import serviceimpl.tag.imageServiceImpl;

public class ImageController implements imageService {
    imageServiceImpl impl=new imageServiceImpl();
    public String saveTag(String jsonData){
        return impl.saveTag(jsonData);
    }

    public String receiveTag(String s){
        return impl.receiveTag(s);
    }

    public void modifyTag(String jsonData){
        impl.modifyTag(jsonData);
    }

    public void initializeTagData(String taskId){
        impl.initializeTagData(taskId);
    }

    public void initializeImgs(String filePath){
        impl.initializeImgs(filePath);
    }

    public String initializeTask(String taskData){
        return impl.initializeTask(taskData);
    }
}

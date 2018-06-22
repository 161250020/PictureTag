package service;

public interface imageService {

    public String saveTag(String jsonData);

    public String receiveTag(String s);

    public void modifyTag(String jsonData);

    public void initializeTagData(String taskId);

    public void initializeImgs(String filePath);

    public String initializeTask(String taskData);

}
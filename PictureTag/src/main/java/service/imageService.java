package service;

import java.util.ArrayList;

public interface imageService {

    public boolean writeTag(String jsonData);

    public ArrayList<String> receiveTag(String s);

    public void modifyTag(String jsonData);

}
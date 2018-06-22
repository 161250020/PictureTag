package service;

import java.util.ArrayList;

public interface BillService {
    public ArrayList<String> BillCounts(String username);
    public ArrayList<String> BillTasks(String username);
}
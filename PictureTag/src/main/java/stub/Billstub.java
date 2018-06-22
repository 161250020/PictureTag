package stub;

import service.BillService;

import java.util.ArrayList;

public class Billstub implements BillService {
    public ArrayList<String> BillCounts(String username){

        return new ArrayList<String>();
    }
    public ArrayList<String> BillTasks(String username){

        return new ArrayList<String>();
    }
}

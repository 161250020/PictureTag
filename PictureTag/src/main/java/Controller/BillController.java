package Controller;

import service.BillService;
import serviceimpl.Bill.BillServiceImpl;

import java.util.ArrayList;

public class BillController implements BillService{
    BillServiceImpl impl=new BillServiceImpl();
    public ArrayList<String> BillCounts(String username){
        return impl.BillCounts(username);
    }
    public ArrayList<String> BillTasks(String username){
        return impl.BillTasks(username);
    }
}

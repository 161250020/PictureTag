package serviceimpl.TestByMonitor;

import serviceimpl.task.FindProjects;

public class Main3 {
    public static void main(String args[]) {
        FindProjects impl = new FindProjects();
        String Date0 = "20180101000201";
        String Date1 = "20180404020302";
        String Date2 = "20180404020303";
        if (impl.checkDate1(Date0, Date1) &&impl.checkDate2(Date2,Date1)){
            System.out.println("OK");
        }
        else{
            System.out.println("NO");
        }
    }
}

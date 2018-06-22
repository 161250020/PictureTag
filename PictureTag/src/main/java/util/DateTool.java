package util;

public class DateTool {
    public static boolean checkDateBefore(String Date1,String Date) {   //"yyyyMMddHHmmss"
        String Date1year = Date1.substring(0,4);              //可能有错误
        String Date1month = Date1.substring(4, 6);
        String Date1day = Date1.substring(6, 8);

        int Date1Year = Integer.parseInt(Date1year);
        int Date1Month = 0;                                    //这部分可以优化;写一个抽象函数
        if (Date1month.charAt(0) == '0') {
            Date1Month = Integer.parseInt(Date1month.substring(1, 2));
        } else {
            Date1Month = Integer.parseInt(Date1month);
        }
        int Date1Day = 0;
        if (Date1day.charAt(0) == '0') {
            Date1Day = Integer.parseInt(Date1day.substring(1, 2));
        } else {
            Date1Day = Integer.parseInt(Date1day);
        }

        String Dateyear = Date.substring(0,4);
        String Datemonth = Date.substring(4, 6);
        String Dateday = Date.substring(6, 8);

        int DateYear = Integer.parseInt(Dateyear);
        int DateMonth = 0;                                    //这部分可以优化;写一个抽象函数
        if (Datemonth.charAt(0) == '0') {
            DateMonth = Integer.parseInt(Datemonth.substring(1, 2));
        } else {
            DateMonth = Integer.parseInt(Datemonth);
        }
        int DateDay = 0;
        if (Dateday.charAt(0) == '0') {
            DateDay = Integer.parseInt(Dateday.substring(1, 2));
        } else {
            DateDay = Integer.parseInt(Dateday);
        }

        //进行比较;
        if (Date1Year > DateYear) {
            return false;
        }
        if (Date1Year < DateYear) {
            return true;
        } else {
            if (Date1Month > DateMonth) {
                return false;
            }
            if (Date1Month < DateMonth) {
                return true;
            } else {
                if (Date1Day > DateDay) {
                    return false;
                }
                if (Date1Day < DateDay) {
                    return true;
                } else {
                    return true;
                }
            }
        }
    }
    public static boolean checkDateAfter(String Date2,String Date) {
        boolean flag = false;
        String Date2year = Date2.substring(0,4);
        String Date2month = Date2.substring(4, 6);
        String Date2day = Date2.substring(6, 8);

        int Date2Year = Integer.parseInt(Date2year);
        int Date2Month = 0;                                    //这部分可以优化;写一个抽象函数
        if (Date2month.charAt(0) == '0') {
            Date2Month = Integer.parseInt(Date2month.substring(1, 2));
        } else {
            Date2Month = Integer.parseInt(Date2month);
        }
        int Date2Day = 0;
        if (Date2day.charAt(0) == '0') {
            Date2Day = Integer.parseInt(Date2day.substring(1, 2));
        } else {
            Date2Day = Integer.parseInt(Date2day);
        }

        String Dateyear = Date.substring(0,4);
        String Datemonth = Date.substring(4, 6);
        String Dateday = Date.substring(6, 8);

        int DateYear = Integer.parseInt(Dateyear);
        int DateMonth = 0;                                    //这部分可以优化;写一个抽象函数
        if (Datemonth.charAt(0) == '0') {
            DateMonth = Integer.parseInt(Datemonth.substring(1, 2));
        } else {
            DateMonth = Integer.parseInt(Datemonth);
        }
        int DateDay = 0;
        if (Dateday.charAt(0) == '0') {
            DateDay = Integer.parseInt(Dateday.substring(1, 2));
        } else {
            DateDay = Integer.parseInt(Dateday);
        }

        //进行比较;
        if (Date2Year > DateYear) {
            return true;
        }
        if (Date2Year < DateYear) {
            return false;
        } else {
            if (Date2Month > DateMonth) {
                return true;
            }
            if (Date2Month < DateMonth) {
                return false;
            } else {
                if (Date2Day > DateDay) {
                    return true;
                }
                if (Date2Day < DateDay) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public static int checkMonth(String month){            //返回该月的天数
        int day=0;
        if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")){
            day=31;
        }
        if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")){
            day=30;
        }
        if(month.equals("02")){
            day=28;
        }
        return day;
    }

    public static int convertDay(String day){                         //将day转为整数
        int result=0;
        if(day.substring(0,1).equals("0")){
            result=Integer.parseInt(day.substring(1,2));
        }
        else{
            result=Integer.parseInt(day);
        }
        return result;
    }

    public static int getMonthIndex(String month){
        int index=0;
        if(month.equals("01")){
            index=0;
        }
        else if(month.equals("02")){
            index=1;
        }
        else if(month.equals("03")){
            index=2;
        }
        else if(month.equals("04")){
            index=3;
        }
        else if(month.equals("05")){
            index=4;
        }
        else if(month.equals("06")){
            index=5;
        }
        else if(month.equals("07")){
            index=6;
        }
        else if(month.equals("08")){
            index=7;
        }
        else if(month.equals("09")){
            index=8;
        }
        else if(month.equals("10")){
            index=9;
        }
        else if(month.equals("11")){
            index=10;
        }
        else{
            index=11;
        }
        return index;
    }

    //辅助方法,计算间隔时间
    public static int calDate(String Date1,String Date2){
        int days=0;
        String temp1=Date1.substring(4,8);
        String temp2=Date2.substring(4,8);
        if(temp1.substring(0,2).equals(temp2.substring(0,2))){
            days=calDay(temp2.substring(2,4))-calDay(temp1.substring(2,4))+1;
        }
        else{
            int MonthOfDays=calDayOfMonth(temp1.substring(0,2));
            days=MonthOfDays-calDay(temp1.substring(2,4))+calDay(temp1.substring(2,4))+1;
        }
        return days;
    }
    public static int calDay(String str){                  //把日转换成天数
        int result=0;
        if(str.substring(0,1).equals("0")){
            result=Integer.parseInt(str.substring(1,2));
        }
        else{
            result=Integer.parseInt(str);
        }
        return result;
    }
    public static int calDayOfMonth(String month){                    //每一个月的天数
        int days=0;
        if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")){
            days=31;
        }
        if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")){
            days=30;
        }
        if(month.equals("02")){
            days=28;
        }
        return days;
    }
}

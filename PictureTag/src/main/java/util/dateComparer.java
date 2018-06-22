package util;

public class dateComparer {

    public boolean isAfter(String compare,String baseDate){
        boolean flag = false;
        int baseYear = Integer.valueOf(baseDate.substring(0,4));
        int cYear = Integer.valueOf(compare.substring(0,4));
        if(baseYear <= cYear){
            int baseMonth = Integer.valueOf(baseDate.substring(4,6));
            int cMonth = Integer.valueOf(compare.substring(4,6));
            if(baseMonth <= cMonth){
                int baseDay = Integer.valueOf(baseDate.substring(6,baseDate.length()));
                int cDay = Integer.valueOf(compare.substring(6,compare.length()));
                if(baseDay <= cDay){
                    flag = true;
                }
            }
        }
        return flag;
    }

    public boolean isBefore(String compare,String baseDate){
        boolean flag = false;
        int baseYear = Integer.valueOf(baseDate.substring(0,4));
        int cYear = Integer.valueOf(compare.substring(0,4));
        if(baseYear >= cYear){
            int baseMonth = Integer.valueOf(baseDate.substring(4,6));
            int cMonth = Integer.valueOf(compare.substring(4,6));
            if(baseMonth >= cMonth){
                int baseDay = Integer.valueOf(baseDate.substring(6,baseDate.length()));
                int cDay = Integer.valueOf(compare.substring(6,compare.length()));
                if(baseDay >= cDay){
                    flag = true;
                }
            }
        }
        return flag;
    }

}

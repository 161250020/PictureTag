package util;

public class UserTaskCount {
         private String username;
         private static int count=0;
         public UserTaskCount(String username){
             this.username=username;
             count++;
         }

    public void setCount(int count) {
        UserTaskCount.count = count;
    }

    public int getCount() {
        return count;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
             return username;
    }
}

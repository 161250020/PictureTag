package serviceimpl.task;

import serviceimpl.userserviceImpl;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminUser {
          userserviceImpl impl=new userserviceImpl();
          //功能方法
          public ArrayList<UserInfo> getAll() {
              ArrayList<UserInfo> list = impl.getall();
              ArrayList<UserInfo> newList = new ArrayList<UserInfo>();
              for (UserInfo user : list) {
                  if (!user.getUsername().equals("admin")) {
                      newList.add(user);
                  }
              }
              return newList;
          }
          public ArrayList<String> getUserCountByMonth(String month){
              ArrayList<String> count=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<UserInfo> newlist=new ArrayList<UserInfo>();
              int MonthOfDay=checkMonth(month);
              for(UserInfo user:all){
                  if(user.getMonth().equals(month)){
                      newlist.add(user);
                  }
              }
              int counts[]={0,0,0,0,0,0};
              if(MonthOfDay==28||MonthOfDay==30) {
                  for (UserInfo user : newlist) {
                      int day = convertDay(user.getDay());
                      counts[(day - 1) / 5]++;
                  }
              }

              if(MonthOfDay==31){
                  for (UserInfo user : newlist) {
                      int day = convertDay(user.getDay());
                      if(day==31){
                          counts[5]++;
                      }
                      else {
                          counts[(day - 1) / 5]++;
                      }
                  }
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  count.add(temp);
              }
              return count;
          }

          public ArrayList<String> getUserCountByYear(String year){
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<UserInfo> newlist=new ArrayList<UserInfo>();
              for(UserInfo user:all){
                  if(user.getDate().substring(0,4).equals(year)){
                      newlist.add(user);
                  }
              }
              int counts[]={0,0,0,0,0,0,0,0,0,0,0,0};
              for(UserInfo user:newlist){
                   counts[getMonthIndex(user.getMonth())]++;
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  result.add(temp);
              }
              return result;
          }


          //辅助方法
          public int checkMonth(String month){            //返回该月的天数
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

          public int convertDay(String day){                         //将day转为整数
              int result=0;
              if(day.substring(0,1).equals("0")){
                  result=Integer.parseInt(day.substring(1,2));
              }
              else{
                  result=Integer.parseInt(day);
              }
              return result;
          }

          public int getMonthIndex(String month){
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
}

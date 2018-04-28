package util;

public class IDCreator {
          UserTaskCount count;
          public IDCreator(UserTaskCount count){
              this.count=count;
          }
          public String generateID(){
              int number=count.getCount();
              String result="";
              while(number>0){
                   result=number%2+result;
                   number=number/2;
              }
              while(result.length()<10){
                  result=0+result;
              }
              result=count.getUsername()+result;
              return result;
          }
}

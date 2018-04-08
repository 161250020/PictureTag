package vo.Project.Task;

import java.util.ArrayList;

public class Sentence {
         private ArrayList<String> tokens;
         private String raw;
         private int imgid;
         private int sentid;
         public Sentence(){
             this.tokens=new ArrayList<String>();
             this.raw="";
             this.imgid=0;
             this.sentid=0;
         }
         public Sentence(ArrayList<String> target,int imgid,int sentid){
             this.tokens=target;
             this.raw=Connect(tokens);
             this.imgid=imgid;
             this.sentid=sentid;
         }
         public String Connect(ArrayList<String> tokens){
              String str="";
              for(int i=0;i<tokens.size();i++){
                  str+=(tokens.get(i)+" ");
              }
              return str;
         }
         public void setTokens(ArrayList<String> tokens){
             this.tokens=tokens;
         }
         public ArrayList<String> setTokens(){
             return tokens;
         }
         public void setRaw(String raw){
             this.raw=raw;
         }
         public String getRaw(){
             return raw;
         }
         public void setImgid(int imgid) {
             this.imgid = imgid;
         }
         public int getImgid() {
             return imgid;
         }
         public void setSentid(int sentid) {
             this.sentid = sentid;
         }
         public int getSentid() {
              return sentid;
         }
    @Override
    public String toString() {
        return "Sentence [tokens=" + tokens + ", raw=" + raw + ",imgid="+imgid+",sentid="+sentid+"]";
    }
}

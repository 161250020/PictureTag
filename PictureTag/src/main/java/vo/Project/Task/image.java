package vo.Project.Task;

import java.util.List;

public class image {
     List<Integer> sentids;
     String imgid;
     List<Sentence> Sentences;
     String url;
     String filename;
     String split;
     boolean flag;//标志图片是否已经被标记

    public image(){}    //空方法很重要
     public image(List<Integer> sentids, String imgid, List<Sentence> Sentences, String url, String split, String filename){
         this.sentids=sentids;
         this.imgid=imgid;
         this.Sentences=Sentences;
         this.url=url;
         this.split=split;                      //训练集(train)or测试集(test)
         this.filename=filename;
     }
     public void setSentids(List<Integer> sentids){
         this.sentids=sentids;
     }
     public List<Integer> getSentids(){
         return this.sentids;
     }
     public void setId(String imgid){
         this.imgid=imgid;
     }

     public String getId() {
        return imgid;
     }
     public void setSentences(List<Sentence> Sentences) {
        this.Sentences =Sentences;
     }
     public List<Sentence> getSentences() {
        return this.Sentences;
     }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl(){
         return this.url;
    }
    public void setSplit(String split) {
        this.split = split;
     }
     public String getSplit() {
        return this.split;
     }
     public void setFilename(String filename){
         this.filename=filename;
     }
     public String getFilename(){
         return this.filename;
     }
    public String getImgid() { return imgid;}
    public void setImgid(String imgid) { this.imgid = imgid; }
    public boolean isFlag() { return flag; }
    public void setFlag(boolean flag) { this.flag = flag; }
    @Override
    public String toString() {
        return "imageService [sentids=" + sentids + ", imgid=" + imgid + ",Sentences="+Sentences+",split="+split+",filename="+filename+"]";
    }

}

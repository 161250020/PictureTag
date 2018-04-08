package vo.Project.Task;

import java.util.List;

public class images {
          List<image> images;
          public images(){};
          public images(List<image> images){
              this.images=images;
          }
          public void setImages(List<image> images){
              this.images=images;
          }
          public List<image> getImages() {
              return images;
          }
    @Override
     public String toString() {
               return "images [images=" + images +  "]";
            }

}

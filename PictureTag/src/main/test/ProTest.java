
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.Project.Project;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class ProTest {
    userserviceImpl impl=new userserviceImpl();
    FindProjects service=new FindProjects();
    @Before
    public void init(){
        Project pro=new Project("gy","0001",1,"wqo");
        service.lauchPro(pro);
    }
    @Test
    public void test1(){
        service.lauchPro(new Project());
    }
    @Test
    public void test2(){
        Project pro=new Project("c","0002",1,"hh");
        service.lauchPro(pro);
        assertEquals(0,service.getPros("hh").size());
    }
    @Test
    public void test3(){
        Project pro=new Project("c","0004",1,"aa");
        service.lauchPro(pro);
        assertEquals(0,service.getPros("aa").size());
    }
    @Test
    public void test4(){
        Project pro=new Project("c","0004",1,"aa");
        service.lauchPro(pro);
        service.update(new Project("c","0004",1,"aa"));
    }
    @Test
    public void test5(){
        Project pro=new Project("d","0002",1,"ss");
        service.lauchPro(pro);
        service.update(new Project("c","0002",1,"dd"));
    }
    @Test
    public void test6(){

        assertEquals(0,service.getPros("wqo").size());
    }

    @After
    public void end(){
        File file=new File("src/main/test/_Projects.txt");
        file.delete();
        File file2=new File("src/main/test/hh_Protects.txt");
        file2.delete();
        File file3=new File("src/main/test/wqo_Protects.txt");
    }
}


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
    }
    @Test
    public void test3(){


    }
    @Test
    public void test4(){


    }
    @Test
    public void test5(){


    }
    @Test
    public void test6(){

        assertEquals("0001",service.getProjects("wqo").get(0).getName());
    }

    @After
    public void end(){
        File file=new File("src/main/test/_Projects.txt");
        file.delete();
    }
}

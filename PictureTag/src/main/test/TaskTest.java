import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import serviceimpl.task.taskServiceImpl;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class TaskTest {
    userserviceImpl impl=new userserviceImpl();
    taskServiceImpl service=new taskServiceImpl();
    @Before
    public void init(){
        impl.register("2","2","","");
        impl.register("3","2","","");
        impl.register("5","2","","");
        impl.register("4","2","","");
    }
    @Test
    public void test1(){
        impl.register("1","2","","");
        assertEquals("2",impl.getUser("1").getPassword());
    }
    @Test
    public void test2(){
        impl.register("gy","gygy","wlx家的小奶狗","葛宇");
        assertEquals("gygy",impl.getUser("gy").getPassword());
    }
    @Test
    public void test3(){
        impl.register("wlx","123","","");
        assertEquals("123",impl.getUser("wlx").getPassword());
    }
    @Test
    public void test4(){
        impl.register("gygy","199761","wlx家的小奶狗","葛宇");
        assertEquals("199761",impl.getUser("gygy").getPassword());
    }
    @Test                                        //检验能否判断重名
    public void test5(){
        assertEquals(false,impl.register("3","5","",""));
    }
    @Test
    public void test6(){
        assertEquals(false,impl.register("2","5","",""));
    }

    @After
    public void end(){
        File file=new File("src/main/test/user.txt");
        file.delete();
    }
}

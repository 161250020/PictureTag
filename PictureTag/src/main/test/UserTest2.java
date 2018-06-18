import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class UserTest2 {
    userserviceImpl impl=new userserviceImpl();
    @Before
    public void init(){
        impl.register("2","2","","");
        impl.register("3","2","","");
        impl.register("5","2","","");
        impl.register("4","2","","");
    }
    @Test
    public void test1(){
        assertEquals(true,impl.delete("2"));
    }
    @Test
    public void test2(){
        assertEquals(false,impl.delete("6"));
    }
    @Test
    public void test3(){
        assertEquals(true,impl.delete("4"));
    }
    @Test
    public void test4(){
        assertEquals(false,impl.delete("gygy"));
    }

    @After
    public void end(){
        File file=new File("src/main/test/user.txt");
        file.delete();
    }
}

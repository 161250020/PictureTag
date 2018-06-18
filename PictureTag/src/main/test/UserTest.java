import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.UserInfo;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class UserTest {
    userserviceImpl impl=new userserviceImpl();
    @Before
    public void init(){

    }
    @Test
    public void test1(){
        impl.register("1","2","","");
        assertEquals(impl.getall().size(),2);
    }
    @Test
    public void test2(){
        impl.login("1","2");
        assertEquals(true,impl.login("1","2"));
    }
    @Test
    public void test3(){
        impl.delete("1");
        assertEquals(1,impl.getall().size());
    }
    @Test
    public void test4(){
        impl.register("gy","199761","wlx家的小奶狗","葛宇");
        assertEquals(2,impl.getall().size());
    }
    @After
    public void end(){
        File file=new File("user.txt");
        file.delete();
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vo.UserInfo;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class UserTest4 {
    userserviceImpl impl=new userserviceImpl();
    @Before
    public void init(){
        impl.register("2","2","","");
        impl.register("3","2","","");
        impl.register("5","2","","");
        impl.register("4","2","","");
        impl.register("1","4","","");
    }
    @Test
    public void test1(){
        UserInfo user=impl.getUser("2");
        user.setPassword("1111");
        impl.update(user);
        assertEquals("1111",impl.getUser("2").getPassword());
    }
    @Test
    public void test2(){
        UserInfo user=impl.getUser("5");
        user.setNickname("hh");
        impl.update(user);
        assertEquals("hh",impl.getUser("5").getNickname());
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

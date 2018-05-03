package junitdemo;

import org.junit.*;

public class Test2 {
    @BeforeClass
    public static void beforeClass(){
        System.out.println("this is Test2 beforeClass");
    }
    @Before
    public void before(){
        System.out.println("this is Test2 before");
    }

    @Test
    public void fun1(){
        System.out.println("this is Test2 fun1");
    }

    @Test
    public void fun2(){
        System.out.println("this is Test2 fun2");
    }

    @After
    public void after(){
        System.out.println("this is Test2 after");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("this is Test2 afterClass");
    }
}

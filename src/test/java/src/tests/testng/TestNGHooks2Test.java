package src.tests.testng;

import org.testng.annotations.*;

public class TestNGHooks2Test {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("beforeSuite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("\tbeforeTest");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("\t\tbeforeClass");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("\t\t\tbeforeMethod");
    }

    @Test
    public void testA2(){
        System.out.println("\t\t\t\tTestA2");
    }

    @Test()
    public void testA1(){
        System.out.println("\t\t\t\tTestA1");
    }
}

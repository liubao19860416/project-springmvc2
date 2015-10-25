import org.junit.Test;

public class MyTest {

    /**
     * 结果相等
     */
    @Test
    public void testName() throws Exception {
        String st1 = "liubao";
        String st2 = "liubao";
        String[] strs={"liubao"};
        System.out.println(st1.hashCode() == st2.hashCode());
        System.out.println(strs[0].hashCode() == st2.hashCode());
    }
}

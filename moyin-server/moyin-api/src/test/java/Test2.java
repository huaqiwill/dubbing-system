import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class Test2 {
    @Test
    public void test1() {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);


        set2.removeAll(set1);

        System.out.println(set2);
    }
}

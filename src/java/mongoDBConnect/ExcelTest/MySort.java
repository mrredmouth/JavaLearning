package mongoDBConnect.ExcelTest;

/***Created by moyongzhuo
 *On 2017/10/20  ***12:03.
 ******/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySort {

    public static List<Integer> max4(List<Integer> lst) {
        if (lst.size() <= 4)
            return lst;
        int a = lst.remove(lst.size() - 1);

        List<Integer> b = max4(lst);
        //System.out.println(b);
        for (int i = 0; i < b.size(); i++) {
            int t = b.get(i);
            if (a > t) {
                //System.out.println(a + " : " + t);
                lst.set(i, a);
                a = t;
            }
        }
        return b;
    }
    public static void main(String[] args) {
        List<Integer> lst = new ArrayList<Integer>();
        lst.addAll(Arrays.asList(12, 127, 85, 66, 27, 34, 15, 344, 156, 344, 29,47));
        System.out.println(max4(lst));
    }
}
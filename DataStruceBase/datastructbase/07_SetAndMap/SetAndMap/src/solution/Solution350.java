package solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Created by 63042 on 2019/7/6.
 * LeetCode350
 * 给定两个数组，编写一个函数来计算它们的交集。

    示例 1:

    输入: nums1 = [1,2,2,1], nums2 = [2,2]
    输出: [2,2]
    示例 2:

    输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
    输出: [4,9]
    说明：

    输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
    我们可以不考虑输出结果的顺序。

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/intersection-of-two-arrays-ii
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    思路：
    可以重复，有需要快速查找重复次数，使用map。
    统计一个数组的每个元素对应的个数，存入一个map中，
    然后遍历第二个数组，查找map中有没有，有则添加进返回值数组中，没有则跳过；添加后需要将map中频次统计次数-1；
 */
public class Solution350 {
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length < nums2.length){
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }

        // key存储数组中元素，value存储元素出现的频次
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums1) {
            if (!map.containsKey(num))
                map.put(num, 1);
            else
                map.put(num, map.get(num) + 1);
        }

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int num : nums2) {
            if (map.containsKey(num)){
                arrayList.add(num);
                map.put(num, map.get(num) - 1);
                if (map.get(num) == 0)
                    map.remove(num);
            }
        }

        int[] res = new int[arrayList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = arrayList.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        Solution350 s = new Solution350();
        int[] res = s.intersect(new int[]{1,2}, new int[]{1,1});
        System.out.println(Arrays.toString(res));
    }
}

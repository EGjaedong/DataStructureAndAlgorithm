package solution;

import java.util.*;

/**
 * Created by 63042 on 2019/7/6.
 * LeetCode349
 * 给定两个数组，编写一个函数来计算它们的交集。

     示例 1:

     输入: nums1 = [1,2,2,1], nums2 = [2,2]
     输出: [2]
     示例 2:

     输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     输出: [9,4]
     说明:

     输出结果中的每个元素一定是唯一的。
     我们可以不考虑输出结果的顺序。

     来源：力扣（LeetCode）
     链接：https://leetcode-cn.com/problems/intersection-of-two-arrays
     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


    思路：
    因为不允许重复，首先想到使用集合。
    将一个数组的所有元素放入一个集合中，同时遍历另外一个数组，检查元素是否在集合中：
        如果在，则添加到待返回的列表中，同时为了避免后续遍历仍有相同元素，可以在添加完成后，将集合中该元素删除。
        如果不再，则进入下一次循环
 */
public class Solution349 {
    public int[] intersection1(int[] nums1, int[] nums2) {
        TreeSet<Integer> set1 = new TreeSet<>();
        for (int num : nums1) {
            set1.add(num);
        }
        TreeSet<Integer> set2 = new TreeSet<>();
        for (int num: nums2){
            set2.add(num);
        }

        set1.retainAll(set2);
        int[] res = new int[set1.size()];
        int i = 0;
        for (int s: set1) {
            res[i++] = s;
        }
        return res;
    }

    public int[] intersection2(int[] nums1, int[] nums2) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums1) {
            set.add(num);
        }

        ArrayList<Integer> list = new ArrayList<>();
        // 遍历第二个数组，同时判断集合中是否有相同元素
        for (int num : nums2) {
            if (set.contains(num)){
                list.add(num);
                set.remove(num);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    public int[] intersection3(int[] nums1, int[] nums2) {
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        for (int num: nums1) {
            arrayList1.add(num);
        }
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (int num: nums2){
            arrayList2.add(num);
        }
        arrayList1.retainAll(arrayList2);
        TreeSet<Integer> set = new TreeSet<>(arrayList1);
        int[] res = new int[set.size()];
        int i = 0;
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            res[i] = iterator.next();
            i++;
        }

        return res;
    }
}

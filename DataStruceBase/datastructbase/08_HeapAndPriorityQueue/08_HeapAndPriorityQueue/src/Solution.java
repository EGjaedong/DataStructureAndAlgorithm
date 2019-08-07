import java.util.*;

/**
 * Created by 63042 on 2019/7/14.
 * LeetCode347
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

     示例 1:

     输入: nums = [1,1,1,2,2,3], k = 2
     输出: [1,2]
     示例 2:

     输入: nums = [1], k = 1
     输出: [1]
     说明：

     你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
     你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。

     来源：力扣（LeetCode）
     链接：https://leetcode-cn.com/problems/top-k-frequent-elements
     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    思路：
        要求算法时间复杂度优于 O(nlogn)，因此，不能使用双重for循环，来找出 N 个元素中前 M 个频率最高的元素。

        找前k个出现频率最高的元素，要将所有元素的频次统计出来，设计一个map存储元素和对应的频次。
        遍历map集合，将最多的前k个元素存放在一个优先队列中（实际上就是放在一个大根堆中），按顺序取出其中元素即可。
        在维护优先队列的时候，需要考虑如何定义此优先队列的优先级，方案如下：
            因为只需要找前k个频次最高的元素，因此优先队列中只需要存放k个元素即可，
            优先级可定义为频次小的优先级高，这样队列的堆首就是队列中k个元素频次最低的元素，
            如果新来的元素的频次要高于队首元素，此时队首元素出队，将这个新来的入队，
        循环遍历map后，前k个元素统计结果就存放在了队列中，将这些元素存放在list中，返回即可。
 */
public class Solution {
    private class Freq implements Comparable<Freq>{
        public int e, freq;

        public Freq(int e, int freq){
            this.e = e;
            this.freq = freq;
        }

        // 频次越小，优先级越高，放在队首，也就是堆的根
        @Override
        public int compareTo(Freq another) {
            if (this.freq < another.freq)
                return 1;
            else if (this.freq > another.freq)
                return -1;
            else
                return 0;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        // 存放元素和频次组成的键值，键为元素，值为频次
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // 统计频次
        for (int num : nums) {
            if (map.containsKey(num))
                map.put(num, map.get(num) + 1);
            else
                map.put(num, 1);
        }

        // 将前k个频次元素存进队列中
        PriorityQueue<Freq> queue = new PriorityQueue<>();
        for (int key : map.keySet()) {
            if (queue.getSize() < k)
                queue.enqueue(new Freq(key, map.get(key)));
            else if (map.get(key) > queue.getFront().freq){
                queue.dequeue();
                queue.enqueue(new Freq(key, map.get(key)));
            }
        }

        // 存进list中
        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()){
            // 这个题，不要求元素按照频次排序，所以添加到开头或结尾都可以。
            list.add(queue.dequeue().e);
            // list.addFirst(queue.dequeue().e);
        }
        return list;
    }
}

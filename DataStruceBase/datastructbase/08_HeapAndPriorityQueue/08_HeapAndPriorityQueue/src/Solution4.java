import java.util.*;
import java.util.PriorityQueue;

/**
 * Created by 63042 on 2019/7/14.
 * LeetCode347
 * 使用java的PriorityQueue
 * java的priorityQueue是最小堆实现的
 * 使用比较器
 * 匿名内部类
 */
public class Solution4 {
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
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return map.get(a) - map.get(b);
            }
        });
        for (int key : map.keySet()) {
            if (queue.size() < k)
                queue.add(key);
            else if (map.get(key) > map.get(queue.peek())){
                queue.remove();
                queue.add(key);
            }
        }

        // 存进list中
        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()){
            // 这个题，不要求元素按照频次排序，所以添加到开头或结尾都可以。
            list.add(queue.poll());
            // list.addFirst(queue.dequeue().e);
        }
        return list;
    }
}

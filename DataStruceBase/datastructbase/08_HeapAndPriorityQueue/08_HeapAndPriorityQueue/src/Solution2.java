import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.PriorityQueue;

/**
 * Created by 63042 on 2019/7/14.
 * LeetCode347
 * 使用java的PriorityQueue
 * java的priorityQueue是最小堆实现的
 */
public class Solution2 {
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
                return -1;
            else if (this.freq > another.freq)
                return 1;
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
            if (queue.size() < k)
                queue.add(new Freq(key, map.get(key)));
            else if (map.get(key) > queue.peek().freq){
                queue.remove();
                queue.add(new Freq(key, map.get(key)));
            }
        }

        // 存进list中
        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()){
            // 这个题，不要求元素按照频次排序，所以添加到开头或结尾都可以。
            list.add(queue.poll().e);
            // list.addFirst(queue.dequeue().e);
        }
        return list;
    }
}

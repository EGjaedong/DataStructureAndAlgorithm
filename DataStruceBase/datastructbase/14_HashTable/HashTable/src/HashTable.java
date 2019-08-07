import java.util.TreeMap;

/**
 * Created by 63042 on 2019/7/10.
 * 自定义HashTable
 * HashTable实质上就是一个数组，存储的元素通过hash函数，计算出一个数值，对应数组中的某个索引值。
 * 将元素存储进该索引即可。
 *
 * 解决哈希冲突：
 *      jdk1.8的方法：起始数组中存储的是链表，有hash冲突时，将冲突的元素挂接到对应索引的链表上。
 *          当链表的平均长度达到一定程度时，将链表改为红黑树，也就是TreeMap。
 */
public class HashTable<K, V> {
    // 定义常量，用于哈希表扩容
    // 最大可容忍平均哈希冲突数量，平均哈希冲突大于此数量时，进行扩容
    private static final int UPPERTOL = 10;
    // 最小可容忍平均哈希冲突数量，小于此值，缩容
    private static final int LOWERTOL = 2;
    // 初始容量
    private static final int INIT_CAPACITY = 7;

    // 存储TreeMap的数组，也就是哈希表本身。
    private TreeMap<K, V>[] hashTable;
    private int size;
    // 用来计算哈希值的素数。
    private int M;

    public HashTable(int M){
        this.M = M;
        size = 0;
        // 初始化数组
        this.hashTable = new TreeMap[M];
        // 初始化数组中的每一个TreeMap
        for (int i = 0; i < M; i++) {
            hashTable[i] = new TreeMap<K, V>();
        }
    }

    public HashTable(){
        this(INIT_CAPACITY);
    }

    // 辅助函数，用于计算哈希值
    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    // 添加操作
    public void add(K key, V value){
        // 取出key对应的哈希值索引中存储的TreeMap
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        // 判断是否存在，存在更新；不存在添加
        if (treeMap.containsKey(key))
            treeMap.put(key, value);
        else {
            treeMap.put(key, value);
            size++;

            if (size >= UPPERTOL * M)
                resize(M*2);
        }
    }

    // 删除
    public V remove(K key){
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        V ret = null;
        if (treeMap.containsKey(key)){
            ret = treeMap.remove(key);
            size--;

            if (size <= LOWERTOL * M && M / 2 > 7)
                resize(M/2);
        }
        return ret;
    }

    // 更改容量
    private void resize(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; i++) {
            newHashTable[i] = new TreeMap<K, V>();
        }

        int oldM = M;
        this.M = newM;
        TreeMap<K, V> temp;

        for (int i = 0; i < oldM; i++) {
            temp = hashTable[i];
            for (K key : temp.keySet()) {
                newHashTable[hash(key)].put(key, temp.get(key));
            }
        }

        this.hashTable = newHashTable;
    }

    public void set(K key, V newValue){
        TreeMap<K, V> treeMap = hashTable[hash(key)];
        if (!treeMap.containsKey(key))
            throw new IllegalArgumentException(key + " dons't exist!");
        treeMap.put(key, newValue);
    }

    public boolean contains(K key){
        return hashTable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashTable[hash(key)].get(key);
    }


}

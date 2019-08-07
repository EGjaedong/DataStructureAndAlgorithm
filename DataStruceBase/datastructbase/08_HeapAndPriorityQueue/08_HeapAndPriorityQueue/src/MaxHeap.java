/**
 * Created by 63042 on 2019/7/14.
 * 数组实现最大堆
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity){
        data = new Array<E>(capacity);
    }

    public MaxHeap(){
        data = new Array<E>();
    }

    /**
     * 构造函数，将一个数组构建成一个堆，Heapify。
     * 两种做法：
     * 1、扫描一遍数组，添加到一个空堆中。O(nlogn)
     * 2、将该数组看成一个堆，从最后一个非叶子节点开始向前遍历，遍历中对元素进行下沉操作（使其及其子树符合堆的性质）。
     *      此过程称为Heapify，时间复杂度为O(n)（记住，不会算）。
     */
    public MaxHeap(E[] arr){
        data = new Array<E>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    // 返回堆中元素个数
    public int size(){
        return data.getSize();
    }

    // 判断是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index){
        if (index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index-1)/2;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return index * 2 + 1;
    }

    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index){
        return index * 2 + 2;
    }

    // 向堆中添加元素
    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    // 上浮
    private void siftUp(int k){
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0){
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    // 看堆中的最大元素
    public E findMax(){
        if (data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when heap is empty.");
        return data.get(0);
    }

    // 取出堆中最大元素
    public E extractMax(){
        E ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    // 下沉
    private void siftDown(int k){
        // 循环条件为如果k有左孩子，进行循环。没有左孩子，说明k所代表的索引，已经是叶子节点的索引
        // 因为完全二叉树性质，k的左孩子不可能等于元素个数，要么小，要么越界。
        while (leftChild(k) < data.getSize()){
            // 代表大的子节点，一开始让其指向左孩子
            int bigger = leftChild(k);

            // 判断，如果当前有有孩子，并且右孩子比左孩子大，则bigger指向右孩子
            // 任何一个条件不满足，都和左孩子进行比较。
            if (bigger + 1 < data.getSize() && data.get(bigger + 1).compareTo(data.get(bigger)) > 0)
                bigger = bigger + 1;
            // 此时data[bigger]指向k的左右孩子中大的

            if (data.get(k).compareTo(data.get(bigger)) >= 0)
                break;

            data.swap(k, bigger);
            k = bigger;
        }
    }

    // 取出堆中的最大元素，并且替换成元素e
    private E replace(E e){
        E ret = findMax();

        data.set(0, e);
        siftDown(0);
        return ret;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

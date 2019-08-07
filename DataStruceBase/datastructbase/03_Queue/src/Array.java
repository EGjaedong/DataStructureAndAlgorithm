/**
 * Created by 63042 on 2019/5/28.
 * 泛型数组 & 动态数组
 */
public class Array<E> {
    private E[] data;
    // size指向数组当前第一个空位置，即永远在实际元素后面一个。size本身的值就是当前数组中的元素个数。
    private int size;

    // 构造函数，传入数组的容量capacity构造Array
    public Array(int capacity){
        data = (E[]) new Object[capacity];
        size = 0;
    }

    // 无参的构造函数，默认初始容量10
    public Array(){
        this(10);
    }

    // 返回数组的容量
    public int getCapacity(){
        return data.length;
    }

    // 返回数组中元素个数
    public int getSize(){
        return size;
    }

    // 返回数组是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    // 向数组末尾添加元素
    public void addLast(E e){
        add(size, e);
    }

    // 向数组开头添加元素
    public void addFirst(E e){
        add(0, e);
    }

    // 向数组中指定位置插入元素。
    // 注意，原本索引位置的元素和其后的元素要整体向后移动
    public void add(int index, E e){
        if (size == data.length)
            resize(2 * data.length);

        // 要插入位置不合法
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");

        for (int i = size - 1; i >= index; i--) {
            data[i+1] = data[i];
        }

        data[index] = e;

        // 维护size，向后移动一位
        size++;
    }

    // 查询指定索引的元素
    public E get(int index){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size - 1);
    }

    // 设置指定位置元素
    public void set(int index, E e){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = e;
    }

    // 查找数组中是否含有元素e
    public boolean contains(E e){
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])){
                return true;
            }
        }
        return false;
    }

    // 查找第一个e的索引，如果没有返回-1
    public int find(E e){
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])){
                return i;
            }
        }
        return -1;
    }

    // 从数组中删除index位置的元素, 返回删除的元素
    public E remove(int index){
        if (index < 0 || index >= size){
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        }
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        // 使用lazy的策略进行缩容，防止复杂度震荡
        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length/2);
        return ret;
    }

    // 删除第一个元素，返回删除的元素
    public E removeFirst(){
        return remove(0);
    }

    // 删除最后一个元素，返回删除的元素
    public E removeLast(){
        return remove(size - 1);
    }

    // 删除指定元素
    public void removeElement(E e){
        int index = find(e);
        if (index != -1){
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d , capacity = %d\n", size, data.length));
        res.append("[");
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }
        res.append("]");
        return res.toString();
    }

    // 将数组空间的容量变成newCapacity大小
    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}

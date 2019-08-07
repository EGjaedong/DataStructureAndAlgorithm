/**
 * Created by 63042 on 2019/6/8.
 * 链表
 */
public class LinkedList<E> {

    /**
     * 节点类
     */
    private class Node{
        private E e;
        private Node next;

        public Node(E e, Node next){
            this.e = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    // 使用虚拟头节点，指向链表第一个元素前的一个元素，值为null。也就是指向索引为-1的元素。
    private Node dummyHead;
    private int size;

    public LinkedList(){
        this.dummyHead = new Node(null, null);
        this.size = 0;
    }

    // 判断是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    // 返回元素个数
    public int getSize(){
        return size;
    }

    // 在链表的index(0-based)位置添加新的元素e
    // 在链表中不是一个常用的操作，练习用
    public void add(int index, E e){
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");

        Node prev = dummyHead;
        // 从-1开始索引，一共需要移动index次
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        /*Node node = new Node(e);
        node.next = prev.next;
        prev.next = node;*/

        prev.next = new Node(e, prev.next);
        size++;
    }

    // 在链表头添加元素
    public void addFirst(E e){
        add(0, e);
    }

    // 在链表末尾添加元素
    public void addLast(E e){
        add(size, e);
    }

    // 获得链表的第index(0-based)个位置的元素
    // 在链表中不是一个常用的操作，练习用
    public E get(int index){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Illegal index.");

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    // 获取链表的第一个元素
    public E getFirst(){
        return get(0);
    }

    // 获取链表最后一个元素
    public E getLast(){
        return get(size-1);
    }

    // 修改链表的第index(0-based)个位置的元素为e
    // 在链表中不是一个常用的操作，练习用
    public void set(int index, E e){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Illegal index.");

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    // 查找链表中是否有元素e
    public boolean contains(E e){
        Node cur = dummyHead.next;
        while (cur != null){
            if (cur.e == e){
                return true;
            }
        }
        return false;
    }

    // 从链表中删除index(0-based)位置的元素, 返回删除的元素
    // 在链表中不是一个常用的操作，练习用
    public E remove(int index){
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Index is illegal.");

        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node rtNode = prev.next;
        prev.next = rtNode.next;
        size--;
        return rtNode.e;
    }

    // 从链表中删除第一个元素, 返回删除的元素
    public E removeFirst(){
        return remove(0);
    }

    // 从链表中删除最后一个元素，返回删除的元素
    public E removeLast(){
        return remove(size-1);
    }

    // 从链表中删除元素e
    public void removeElement(E e){
        Node prev = dummyHead;
        while (prev.next != null){
            if (prev.next.e.equals(e))
                break;

            prev = prev.next;
        }
        if (prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node cur = dummyHead.next;
        while (cur != null){
            sb.append(cur + "->");
            cur = cur.next;
        }
        sb.append("NULL");
        return sb.toString();
    }
}

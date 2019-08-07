/**
 * Created by 63042 on 2019/5/31.
 * 循环队列，底层使用循环数组。
 * 定义两个变量，front 和 tail。
 * front：标记队列队首，第一个元素
 * tail：标记队列队尾，指向最后一个元素的下一位
 * front == tail 表明当前队列为空
 * (tail + 1) % data.length == front 表明当前队列满，data.length表示数组的实际长度
 * -------------------------------------------------
 * 下图，数字表示数组序号。
 * front
 *   |
 * [ 0 ] [ 1 ] [ 2 ] [ 3 ] [ 4 ] [ 5 ] [ 6 ] [ 7 ]
 *   |
 * tail
 * 上述情况表示当前队列为空
 * -------------------------------------------------
 *              front
 *               |
 * [ 0 ] [ 1 ] [ 2 ] [ 3 ] [ 4 ] [ 5 ] [ 6 ] [ 7 ]
 *         |
 *       tail
 * 上述情况表示当前队列为满，tail处为空，但不能向其中添加元素，因此创建数组时，需要capacity + 1。
 * =================================================
 * 当前队列有4个元素，如情况1所示：
 * 1、
 * front
 *   |
 * [ a ] [ b ] [ c ] [ d ] [   ] [   ] [   ] [   ]
 *                           |
 *                          tail
 * 入队时向tail中添加，此时添加e，tail向后移动，变成情况2：
 * -------------------------------------------------
 * 2、
 * front
 *   |
 * [ a ] [ b ] [ c ] [ d ] [ e ] [   ] [   ] [   ]
 *                                 |
 *                                tail
 * 出队时front元素出队，front向后移动，变成情况3：
 * -------------------------------------------------
 * 3、
 *        front
 *         |
 * [   ] [ b ] [ c ] [ d ] [ e ] [   ] [   ] [   ]
 *                                 |
 *                                tail
 * 若出现如下情况4，即当前tail指向数组队后一位，但已出队两个元素。
 * -------------------------------------------------
 * 4、
 *             front
 *               |
 * [   ] [   ] [ c ] [ d ] [ e ] [ f ] [ g ] [   ]
 *                                             |
 *                                           tail
 * 此时，向队列中添加元素，最后一个空位被占；又因前面还有空位，tail就指向了数组第一位，如情况5所示：
 * -------------------------------------------------
 * 5、
 *             front
 *               |
 * [   ] [   ] [ c ] [ d ] [ e ] [ f ] [ g ] [ h ]
 *   |
 * tail
 * 再添加一个元素，tail向后移动一位，如情况6所示：
 * -------------------------------------------------
 * 6、
 *             front
 *               |
 * [ i ] [   ] [ c ] [ d ] [ e ] [ f ] [ g ] [ h ]
 *         |
 *       tail
 * 此时，(tail + 1) % data.length == front，表示队列满。再添加元素即需要对数组进行扩容。
 *
 * =================================================
 *
 * 对于扩容：
 * 进行扩容时，直接将front开始到tail结尾的元素拷贝到新数组中，从0开始即可。
 * 将front指向0，tail指向size即可。
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    private int front, tail;
    private int size;

    public LoopQueue(int capacity){
        // 为什么要 capacity + 1？
        // 看上边注释
        data = (E[]) new Object[capacity + 1];
    }

    public LoopQueue(){
        data = (E[]) new Object[11];
    }

    @Override
    public int getSize() {
        return size;
    }

    public int getCapacity(){
        // 初始化时 容量+1
        return data.length - 1;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {
        // 当前队列满，进行扩容
        if ((tail + 1) % data.length == front)
            resize(getCapacity() * 2);
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;

        // 触发条件后，缩容
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity()/2);
        return ret;
    }

    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(i+front) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E getFront() {
        return data[front];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            res.append(data[i]);
            if ((i + 1) % data.length != tail)
                res.append(" ,");
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}

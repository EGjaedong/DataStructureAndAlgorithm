/**
 * Created by 63042 on 2019/5/31.
 * 队列接口，定义出队，入队，查看队首元素，获取元素个数，判断是否为空
 */
public interface Queue<E> {
    int getSize();
    boolean isEmpty();
    void enqueue(E e);
    E dequeue();
    E getFront();
}

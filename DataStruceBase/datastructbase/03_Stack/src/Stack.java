/**
 * Created by 63042 on 2019/5/29.
 * 栈接口
 */
public interface Stack<E> {
    public int getSize();
    public boolean isEmpty();
    public void push(E e);
    public E pop();
    public E peek();
}

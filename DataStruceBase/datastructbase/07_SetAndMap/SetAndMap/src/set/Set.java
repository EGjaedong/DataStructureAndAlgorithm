package set;

/**
 * Created by 63042 on 2019/7/5.
 * 自定义Set接口
 */
public interface Set<E> {
    void add(E e);
    void remove(E e);
    boolean contains(E e);
    int getSize();
    boolean isEmpty();
}

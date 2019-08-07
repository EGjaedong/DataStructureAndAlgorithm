import java.util.ArrayList;

/**
 * 红黑树
 * 详细理解过程看笔记
 *
 * 一、红黑树五大性质（定义/性质）：
 *      1、每个节点或者红色的，或者是黑色的。（定义内容）
 *      2、根节点是黑色的。（性质）
 *      3、每一个叶子节点（最后的空节点）是黑色的。（性质）
 *      4、如果一个节点是红色的，那么它的孩子节点都是黑色的。（性质，推导过程由23树推导）
 *      5、任意一个节点到叶子节点，经过的黑色节点是一样的。（性质，最重要。根据23树是绝对平衡树推导得到）
 *
 * 二、2-3树
 *      笔记
 *
 * 三、红黑树与2-3树等价
 *      笔记
 *
 * 四、红黑树添加元素：
 *      笔记
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public boolean color;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public RedBlackTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 判断节点node的颜色
    private boolean isRed(Node node){
        if (node == null)
            return BLACK;
        return node.color;
    }

    /**
     * 左旋转
     * 此步发生在 插入节点大于当前节点 即 当前节点右子节点为红（对应 3节点，左边是根，需要旋转）。
     * 作用是将当前子树变为 2-3树中 3节点对应的红黑树的样子。仅仅负则此过程，不负责后续颜色变化过程。
            node                     x
           /   \     左旋转         /  \
          T1   x   --------->   node   T3
              / \              /   \
             T2 T3            T1   T2
        步骤：
            node.right = x.left;
            x.left = node;

            x.color = node.color; // 保持颜色与原来根颜色一致
            node.color = RED;     // 3节点左侧 对应红黑树 左子节点，因此需要设置成红色。
     * @param node 待旋转
     * @return
     */
    private Node leftRotate(Node node){
        Node x = node.right;

        // 左旋转
        node.right = x.left;
        x.left = node;

        // 新的根颜色于原来根保持一致
        x.color = node.color;
        // 根的左子节点，就是 2-3树中  3节点的左侧节点，对应红黑树红色节点。
        // 左子节点设置为红色
        node.color = RED;

        // 返回左旋转形成的新树
        return x;
    }

    /**
     * 此过程等同于 将 临时4节点 拆分成三个 2节点，之后将根节点向上融合之前的过程。
     * 只负责反转颜色，何时调用由具体逻辑判断
     * @param node
     */
    private void flipColors(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 右旋转
     * 此步发生在 当前节点左子节点和左孙子节点均为红时（对应 临时4节点，需要进行拆分）。
     * 此步骤等同于 将临时4节点 的根从 最右侧 移动到 中间，以供之后进行拆分工作。
              node                   x
             /   \     右旋转       /  \
            x    T2   ------->   y   node
           / \                       /  \
          y  T1                     T1  T2
        步骤：
            node.left = x.right;
            x.right = node;

            x.color = node.color;
            node.color = RED;
     * @param node
     * @return
     */
    private Node rightRotate(Node node){
        Node x = node.left;

        // 右旋转
        node.left = x.right;
        x.right = node;

        // 改变子树颜色
        x.color = node.color;
        node.color = RED;

        return x;
    }


    // 向红黑树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
        // 将根改为黑色的。
        root.color = BLACK;
    }

    // 向以node为根的红黑树中插入元素(key, value)，递归算法
    // 返回插入新节点后红黑树的根
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value); // 默认插入红色节点
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        // 1、查看是否需要进行左旋转
        if (isRed(node.right) && !isRed(node.left))
            node = leftRotate(node);

        // 2、查看是否需要进行右旋转
        if (isRed(node.left) && isRed(node.left.left))
            node = rightRotate(node);

        // 3、查看是否需要进行颜色翻转
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        return node;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key){

        if( node == null )
            return null;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            return node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            return node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            RedBlackTree<String, Integer> map = new RedBlackTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}

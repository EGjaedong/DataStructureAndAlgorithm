import java.util.ArrayList;

/**
 * Created by 63042 on 2019/7/8.
 * AVL树。
 * AVL树对于平衡的定义：
 *  任意节点左右子树的高度差不超过1。
 *
 * 高度：节点高度计算公式：1 + Max(左子树高度，右子树高度)。
 *      空节点高度为0，叶子节点高度为1。
 * 平衡因子：节点左右子树高度差，node.left.height - node.right.height。
 *      空节点平衡因子为0。
 */
public class AVLTree<K extends Comparable<K>, V> {

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public int height; // 记录当前节点高度。高度计算为：1 + Max(左子树高度，右子树高度)。空树高度为0，叶子节点高度为1。

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1; // 初始化Node时，此节点无左右子树，因此高度为1。
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 判断该二叉树是否是一棵二分搜索树
    public boolean isBST(){
        ArrayList<K> keys = new ArrayList<K>();
        inOrder(root, keys);

        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;
        }
        return true;
    }

    // 中序遍历
    private void inOrder(Node node, ArrayList<K> keys){
        if (node == null)
            return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    // 判断该二叉树是否是一棵平衡二叉树
    public boolean isBalanced(){
        return isBalanced(root);
    }

    // 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced(Node node){
        if (node == null)
            return true;

        int balanceFactor = getBalanceFator(node);
        if (Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }


    // 获得节点node的高度
    private int getHeight(Node node){
        if (node == null)
            return 0;
        return node.height;
    }

    // 获得节点node的平衡因子
    private int getBalanceFator(Node node){
        if (node == null)
            return 0;

        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 右旋转操作
        右旋操作用于修正 平衡因子大于1的树，也就是左子树高度比右子树高度多2及以上。（实质上也就只能多个2，因为添加元素一次只添加一个，子树高度变化最多一次+1）
        右旋转操作步骤及合理性证明：
                 y                              x
                / \                           /   \
               x   T4     向右旋转 (y)        z     y
              / \       - - - - - - - ->    / \   / \
             z   T3                       T1  T2 T3 T4
            / \
          T1   T2
        即：  y.left = x.right;
             x.right = y;
        也就是对当前子树进行顺时针（向右）旋转，所以叫右旋转。

        证明是否为二分搜索树：
            旋转前有：元素大小顺序为 T1 < z < T2 < x < T3 < y < T4；
            旋转后有：元素大小顺序为 T1 < z < T2 < x < T3 < y < T4;
        证明是否为平衡二叉树（平衡指的是AVL树定义的平衡，也就是 左右子树高度差不超过1）：
            设 max(T1.height, T2.height) = h，则有
            旋转前：
                z.height = h + 1
                T3.height = h+1 或 h（因为不平衡发生在y上，x上仍然是平衡的）
                x.height = h + 2;
                T4.height = h （因为y上发生了不平衡，且在向左子树添加一个元素时，这种不平衡只能是左子树比右子树高2，即 x.height - T4.height = 2）
            旋转后：
                T1，T2，T3，T4的高度不变
                z.height = h + 1（也不变）   z没动，显然是平衡的（原来就是平衡的）
                y.height = h + 2 或 h + 1   T3.height = h + 1 或 h；T4.height = h，显然y也平衡
                x.height = h + 3 或 h + 2   z.height = h + 1；y.height = h + 2 或 h + 1，显然x也平衡
            说明，右旋转操作过后，原来的以y为根的树变成了以x为根的平衡二叉树

     * 另外，在右旋转操作完成后需要维护x和y节点的高度。
     * @param y 当前节点
     * @return 新的树的根
     */
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;

        // 右旋转过程
        x.right = y;
        y.left = T3;

        // 更新height
        // 要先更新y，再更新x。因为y现在是x的子树，x的高度依赖于y的高度
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        // 返回新的树的根
        return x;
    }

    /**
     * 左旋转
         左旋操作用于修正 平衡因子小于-1的树，也就是右子树高度比左子树高度多2及以上。（实质上也就只能多个2，因为添加元素一次只添加一个，子树高度变化最多一次+1）
         左旋转操作示意：
            y                             x
          /  \                          /   \
         T1   x      向左旋转 (y)       y     z
             / \   - - - - - - - ->   / \   / \
            T2  z                    T1 T2 T3 T4
               / \
              T3 T4
         即： x.left = y;
             y.right = T2;

         原理即合理性证明和右旋转类似。
     * @param y 当前节点
     * @return 新的树的根
     */
    private Node leftRotate(Node y){
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        // 添加元素完成后，节点高度可能已经发生变化，需要重新计算。
        // 不能简单 +1，因为可能有更新操作，此时高度不会发生变化。
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFator(node);
        // 如果平衡因子绝对值大于1，说明此节点已经失衡
        /*if (Math.abs(balanceFactor) > 1)
            System.out.println("unbalance : " + balanceFactor);*/

        // 平衡维护

        // 此种情况称之为 LL ，即左侧的左侧插入节点
        // 1、当前节点平衡因子大于1，说明当前节点不平衡，且是左子树高于右子树
        // 2、当前节点的左子树平衡因子大于等于0，说明当前节点的左子树的 左子树高度大于等于  当前节点左子树的 右子树的高度，
        //      而发生这种变化的原因是向 当前节点的 左子树 的 左子树插入的新的节点，使得左子树的左子树高度发生了变化，进而影响了左子树，最后反应在当前节点上。
        if (balanceFactor > 1 && getBalanceFator(node.left) >= 0)
            // 左子树高于右子树 多于 1，进行右旋转
            return rightRotate(node);

        // 此种情况称之为 RR ，即右侧的右侧插入节点
        // 1、当前节点平衡因子小于-1，说明当前节点不平衡，且是右子树高于左子树
        // 2、当前节点的右子树平衡因子小于0，说明插入节点为向当前节点 右子树的 右子树进行的插入。
        if (balanceFactor < -1 && getBalanceFator(node.right) <= 0)
            // 右子树高于左子树 多于 1，进行左旋转
            return leftRotate(node);

        // LR情况 （向左子树的右子树中插入元素）
        // 先将左子树进行左旋转，将树转化成LL情况，然后将得到的新树进行右旋转。
        // 操作顺序就是 LR
        if (balanceFactor > 1 && getBalanceFator(node.left) < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL情况
        // 先将右子树进行右旋转，将树转化成RR情况，然后将得到的新树进行左旋转。
        // 操作顺序就是 RL
        if (balanceFactor < -1 && getBalanceFator(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

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

        Node retNode;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            retNode = node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            retNode = node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }

            // 待删除节点右子树为空的情况
            else if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }
            else{
                // 待删除节点左右子树均不为空的情况

                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }
        }

        if (retNode == null)
            return null;

        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFator(retNode);

        // 平衡维护

        // LL
        if (balanceFactor > 1 && getBalanceFator(retNode.left) >= 0)
            // 左子树高于右子树 多于 1，进行右旋转
            return rightRotate(retNode);

        // RR
        if (balanceFactor < -1 && getBalanceFator(retNode.right) <= 0)
            // 右子树高于左子树 多于 1，进行左旋转
            return leftRotate(retNode);

        // LR情况
        if (balanceFactor > 1 && getBalanceFator(retNode.left) < 0){
            node.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL情况
        if (balanceFactor < -1 && getBalanceFator(retNode.right) > 0){
            node.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println(map.isBST());
            System.out.println(map.isBalanced());


            for (String word: words){
                map.remove(word);
                if (!map.isBST() || !map.isBalanced())
                    throw new RuntimeException("Error");
            }
        }

        System.out.println();
    }
}

/**
 * Created by 63042 on 2019/6/30.
 * BinarySearchTree
 * 二分搜索树
 * 1、二叉树
 * 2、所有子树根节点大于左侧子树所有节点，同时小于右侧子树所有节点。
 */
public class BSTBeforeImproveAdd<E extends Comparable<E>> {
    private class Node{
        private E e;
        private Node left, right;
        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTBeforeImproveAdd(){
        root = null;
        size = 0;
    }

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    // 向二分搜索树中添加新的元素e
    public void add(E e){
        if (root == null)
            root = new Node(e);
        else
            add(root, e);
    }

    // 向node节点为根的树插入节点e
    private void add(Node node, E e){
        // 考虑递归的终止条件
        // 1、待插入元素与当前节点元素值相同，不做任何操作
        if (e.equals(node.e))
            return;
        // 2、如果待插入元素小于当前节点，且当前节点没有左叶子节点，直接插入到当前节点的左叶子节点处
        else if (e.compareTo(node.e) < 0 && node.left == null){
            node.left = new Node(e);
            size++;
            return;
        }
        // 3、如果待插入元素大于当前节点，且当前节点没有右叶子节点，直接插入
        else if (e.compareTo(node.e) > 0 && node.right == null){
            node.right = new Node(e);
            size++;
            return;
        }

        // 不满足终止条件，进行递归
        // 小于，插入到左子树
        if (e.compareTo(node.e) < 0)
            add(node.left, e);
        // 大于，插入到右子树
        else
            add(node.right, e);
    }
}

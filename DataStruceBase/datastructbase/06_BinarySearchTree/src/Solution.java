/**
 * Created by 63042 on 2019/7/4.
 * LeetCode144，二叉树前序遍历
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    // Definition for a binary tree node.
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 递归实现
    public static List<Integer> preorderTraversal(TreeNode root) {
        if (root == null)
            return null;

        List<Integer> list = new ArrayList<>();
        list = preorderTraversal(root, list);
        return list;
    }

    private static List<Integer> preorderTraversal(TreeNode node, List<Integer> list){
        if (node == null)
            return null;

        list.add(node.val);
        preorderTraversal(node.left, list);
        preorderTraversal(node.right, list);

        return list;
    }

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        n1.left = null;
        n1.right = n2;
        TreeNode n3 = new TreeNode(3);
        n2.right = n3;
        List<Integer> list = preorderTraversal(n1);
        System.out.println(list);
    }
}

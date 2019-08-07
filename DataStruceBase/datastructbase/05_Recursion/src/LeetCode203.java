/**
 * Created by 63042 on 2019/6/14.
 * LeetCode 203题 题解
 * 题目：删除链表中等于给定值 val 的所有节点。
 * 示例：
 *  输入: 1->2->6->3->4->5->6, val = 6
 *  输出: 1->2->3->4->5
 *
 *
 * 明确一个事情：
 *  在执行链表删除操作的时候，需要找到的时待删除节点的前一个节点，使其 next 属性越过要删除的节点。
 *  因此，在循环判断中，要判断的是 prve.next 是否是要删除的节点；如果是，则 prve.next = prve.next.next，越过该节点；不是再向后移动。
 */
public class LeetCode203 {
}

/**
 * 不使用虚拟头节点，单次循环解决
 * 因为要执行的是删除操作，要判断的是 prev.next 节点是否需要删除，
 * 不使用虚拟头节点时，链表的第一个节点为prev起始值，又因为删除操作在循环中判断的时prev.next，
 * 直接开始循环判断会漏掉第一个节点，因此要在开始循环之前，判断第一个节点是否需要删除。找到第一个不需要删除的节点时，使其为prev。
 */
class Solution1{
    public ListNode removeElements(ListNode head, int val) {
        // 头节点不为空，且头节点的值为要删除的值
        // 因为有可能出现开始的几个节点都是要删除的节点，所以这里使用while循环执行多次操作，
        // 而不是使用if仅仅值判断第一个节点。
        while (head != null && head.val == val){
            head = head.next;

            // 考虑空间回收时的写法，解体时没必要这么写
            /*
            ListNode del = head;
            head = head.next;
            del.next = null;
            */
        }

        // 如果此时头节点为空，则直接返回
        if (head == null)
            return head;

        // 头节点不为空，且头节点指向的不是要删除的节点，向后循环，删除后续指定节点。
        ListNode prev = head;
        while (prev.next != null){
            // 如果 prev.next 为要删除节点，则删除。删除操作是将 prev的next指针直接指向删除节点的下一个节点，
            // 此时prev的next指针指向的节点是否需要删除，需要进一步判断。
            // 因此，删除后，prev节点指针不能向后移动。
            if (prev.next.val == val){
                prev.next = prev.next.next;

                /*
                ListNode del = prev.next;
                prev.next = del.next;
                del.next = null;
                */
            }
            else {
                prev = prev.next;
            }
        }
        return head;
    }
}

/**
 * 使用虚拟头节点，单词循环解决
 * 因为要执行的是删除操作，要判断的是 prev.next 节点是否需要删除，
 * 因此，使用虚拟头节点，是prev直接指向虚拟头节点，可以直接开始循环判断 prev.next 是否为要删除节点，并进行后续操作。
 */
class Solution2{
    public ListNode removeElements(ListNode head, int val) {
        // 定义虚拟头节点，使其指向头节点前的一个元素
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;

        ListNode prev = dummyHead;
        while (prev.next != null){
            if (prev.next.val == val)
                prev.next = prev.next.next;
            else
                prev = prev.next;
        }
        return dummyHead.next;
    }
}

class Solution3{
    public ListNode removeElements(ListNode head, int val){
        // 最小问题的解
        if (head == null)
            return null;

        // 更小问题的解
        head.next = removeElements(head.next, val);
        // 判断head是否为val：是则删除，不是则直接返回
        return head.val == val ? head.next : head;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 6, 3, 4, 5, 6};
        ListNode head = new ListNode(nums);
        System.out.println(head);

        ListNode res = (new Solution3()).removeElements(head, 6);
        System.out.println(res);
    }
}

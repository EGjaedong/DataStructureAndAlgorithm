/**
 * Created by 63042 on 2019/7/9.
 * LeetCode387
    给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。

    案例:

    s = "leetcode"
    返回 0.

    s = "loveleetcode",
    返回 2.

    注意事项：您可以假定该字符串只包含小写字母。

    思路：
        定义一个26个元素的数组，每个元素分别代表一个字母（隐式约定，而非显示表明）。
        遍历字符串，统计出现次数存入对应位置；
        再遍历一遍，返回值为1的索引对应的索引。没有返回-1

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/first-unique-character-in-a-string
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int firstUniqChar(String s) {
        if (s.length() == 0)
            return -1;

        int[] arr = new int[26];

        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a'] ++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (arr[s.charAt(i) - 'a'] == 1){
                return i;
            }
        }

        return -1;
    }
}

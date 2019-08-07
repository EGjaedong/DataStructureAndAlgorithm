import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        if (s.length() == 0){
            return true;
        }
        if (s.length() == 1)
            return false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 左括号入栈
            if (c == '(' || c == '[' || c == '{'){
                stack.push(c);
                continue;
            }
            else {
                if (stack.isEmpty())
                    return false;
                // 不是左括号，尝试匹配
                Character topChar = stack.pop();

                if (c == ')' && topChar != '(')
                    return false;
                if (c == ']' && topChar != '[')
                    return false;
                if (c == '}' && topChar != '{')
                    return false;
            }
        }
        // 循环结束，栈为空说明匹配完，返回true；不为空，匹配失败
        return stack.isEmpty();
    }
}
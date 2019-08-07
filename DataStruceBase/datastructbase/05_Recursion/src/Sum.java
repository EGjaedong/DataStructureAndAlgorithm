/**
 * Created by 63042 on 2019/6/25.
 * 递归解决数组求和
 */
public class Sum {
    public static int sum(int[] arr){
        return sum(arr, 0);
    }

    private static int sum(int[] arr, int start){
        // 递归终结条件，也可以说是最小问题求解
        if (start == arr.length)
            return 0;
        return arr[start] + sum(arr, start + 1);
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8};
        int sum = sum(arr);
        System.out.println(sum);
    }

}

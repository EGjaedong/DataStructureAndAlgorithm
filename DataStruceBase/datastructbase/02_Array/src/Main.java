public class Main {

    private static void arrayTest(){
        Array arr = new Array(20);
        for(int i = 0 ; i < 10 ; i ++)
            arr.addLast(i);
        System.out.println(arr);

        arr.add(1, 100);
        System.out.println(arr);

        arr.addFirst(-1);
        System.out.println(arr);
        // [-1, 0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        arr.remove(2);
        System.out.println(arr);

        arr.removeElement(4);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
    }

    public static void arrayGenericTest(){
        // 后面的泛型加不加都行，1.7以后新特性，不加直接写个尖括号也行
        ArrayGeneric<Integer> arr1 = new ArrayGeneric<Integer>();
        for(int i = 0 ; i < 10 ; i ++)
            arr1.addLast(i);
        System.out.println(arr1);

        arr1.add(1, 100);
        System.out.println(arr1);

        arr1.addFirst(-1);
        System.out.println(arr1);
        // [-1, 0, 100, 1, 2, 3, 4, 5, 6, 7, 8, 9]

        arr1.remove(2);
        System.out.println(arr1);

        arr1.removeElement(4);
        System.out.println(arr1);

        arr1.removeFirst();
        System.out.println(arr1);
    }

    public static void main(String[] args) {
        arrayGenericTest();
    }
}

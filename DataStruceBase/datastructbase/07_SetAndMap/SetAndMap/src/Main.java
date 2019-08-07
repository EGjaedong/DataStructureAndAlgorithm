import set.BSTSet;
import set.FileOperation;
import set.LinkedListSet;
import set.Set;

import java.util.ArrayList;

public class Main {
    public static double testSet(Set<String> set, String filename){
        System.out.println(filename);

        long startTime = System.nanoTime();
        ArrayList<String> list = new ArrayList<>();
        if (FileOperation.readFile(filename, list)){
            System.out.println("Total words:" + list.size());
            for (String word :
                    list) {
                set.add(word);
            }
            System.out.println("Total different words:" + set.getSize());
        }
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000000000.0;
    }

    public static void main(String[] args) {
	// write your code here
        String file = "pride-and-prejudice.txt";
        BSTSet<String> bstSet = new BSTSet<>();
        double time1 = testSet(bstSet, file);
        System.out.println(time1);

        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        double time2 = testSet(linkedListSet, file);
        System.out.println(time2);
    }
}

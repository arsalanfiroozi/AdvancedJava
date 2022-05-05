import java.util.*;

public class HashTest {
    public static void main(String[] args) throws Exception {

        //TEST
		int numBuck = 13;
        Hash table = new Hash(numBuck);
		
		table.add(1);
		table.add(2);
		table.add(3);
		table.add(4);
		table.add(5);
		table.add(9);
		table.add(10);
		table.add(11);
		table.add(22);
		table.add(23);
		table.add(24);
		table.add(25);
		table.add(9);
		
		table.remove(3);
		table.remove(4);
		
		for (int i = 0; i < table.numBuck; i++) {
			
			Obj now = table.bucketArray.get(i);
			if (now != null) {
				System.out.println("Num: " + i);
			}
			while (now != null) {
				System.out.println(now.value);
				now = now.next;
			}
		}
		
		int v = 5;
		System.out.println("value " + v + " : " + table.findVal(v));
		v = 4;
		System.out.println("value " + v + " : " + table.findVal(v));

    }
}
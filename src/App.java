import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Q1
        // System.out.println("Start....");
        // Q1 obj = new Q1(0);

        // Q2
        // Integer i;
        // ArrayList<Thread> threads = new ArrayList<Thread>();
        // for(i=0;i<101;i++){
        //     Q2 obj2 = new Q2();
        //     Thread thread = new Thread(obj2);
        //     threads.add(thread);
        // }
        // for(i=0;i<101;i++){
        //     threads.get(i).start();
        // }

        //Q3
        int L = 256;
        int[] A = new int[L];
        Random ran = new Random();
        for (int ki = 0; ki < L ; ki++) {
            // A[i] = i;
            // A[L-i-1] = i;
            A[ki] = -200 + ran.nextInt(401);
        }

        Q3 MS = new Q3();
        int[] sortedA = MS.mergeSort(A, L);

        System.out.println();
        for (int ji = 0; ji < L; ji++) {
            System.out.print(sortedA[ji]+"\t ");
        }

        //check
        for (int li = 0; li < L-1; li++) {
            if (sortedA[li+1]<sortedA[li]) {
                System.out.println("\n\nERROR at i="+li);
                System.out.println("sortedA["+li+"]="+sortedA[li]);
                System.out.println("sortedA["+(li+1)+"]="+sortedA[li+1]);
            }
        }
        

    }
}

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Q3{
    int[] A;
    int L;
    ArrayList<Semaphore> merged = new ArrayList<Semaphore>();

    public Q3(){}

    public class Merger extends Thread {
        int mergerID;

        Merger(int mergerID) {
            this.mergerID = mergerID;
        }

        public void run(){  
            int startIdx;
            int endIdx;
            int p1;
            int p2;
            int len;
            int[] tempA;
            for (int k = 1; ((mergerID+1)%(1<<k))==0 ; k++) {
                startIdx = mergerID - (1<<(k)) + 1;
                endIdx   = mergerID;
                len = endIdx-startIdx+1;
                p1 = 0;
                p2 = len/2;
                tempA =  new int[len];
                try {
                    for (int si = (endIdx-1); si >= startIdx; si--) {
                        merged.get(si).acquire();
                    }
                    try {
                        //System.out.println("merger"+startIdx+"acquired att k="+k+" ap="+merged.get(startIdx).availablePermits()+" by mid= "+mergerID);

                        for (int i = 0; i < len; i++) {
                            tempA[i] = A[startIdx+i];
                        }
                        while ((p1<(len/2)) && (p2<len)) {
                            if (tempA[p1]>tempA[p2]) {
                                A[startIdx+p1+p2 - len/2] = tempA[p2];
                                p2++;
                            } else {
                                A[startIdx+p1+p2 - len/2] = tempA[p1];
                                p1++;
                            }
                        }
                        if (p1 == len/2) {
                            while (p2<len) {
                                A[startIdx+p1+p2 - len/2] = tempA[p2];
                                p2++; 
                            }
                        } else {
                            while (p1<(len/2)) {
                                A[startIdx+p1+p2 - len/2] = tempA[p1];
                                p1++; 
                            } 
                        }


                    } finally {
                        for (int si = startIdx; si < endIdx; si++) {
                            merged.get(si).release();
                        }
                        //System.out.println("merger"+startIdx+"released at k="+k+" ap="+merged.get(startIdx).availablePermits()+" by mid= "+mergerID);
                    }
                } catch (InterruptedException ignored) {}
            }
            merged.get(mergerID).release();;
            // System.out.println("merger"+mergerID+"released ap="+merged.get(mergerID).availablePermits()+" by mid= "+mergerID);
        }  
    }

    public int[] mergeSort(int[] AA, int LL) throws InterruptedException {
        A = AA.clone();
        L = LL;
        ArrayList<Merger> mergers = new ArrayList<Merger>();
        for (int i = 0; i < L; i++) {
            Semaphore m = new Semaphore(0);
            merged.add(m);
        }
        // for (int i = 0; i < L; i++) {
        //     System.out.print(merged.get(i).availablePermits()+"\t ");
        // }

        for (int i = 0; i < L; i++) {
            Merger merger = new Merger(i);
            mergers.add(merger);
            merger.start();
        }
        mergers.get(L-1).join();
        // for (int i = 0; i < L; i++) {
        //     mergers.get(i).join();
        // }
        return A;
    }


}

import java.util.*;

public class Hash {
    ArrayList<Obj> bucketArray;
	int numBuck;
	int numObj;
	
	public Hash(int num) {
		bucketArray = new ArrayList<Obj>();
        numBuck = num;
        numObj = 0;
 
        for (int i = 0; i < numBuck; i++) {
            bucketArray.add(null);
		}
	}
	
	public int size() { 
		return numObj; 
	}
	
    public boolean isEmpty() { 
		return size() == 0; 
	}
	
	public int findBuck(int v) {
		int ans = v % numBuck;
		if (ans < 0 ) {
			ans = ans*(-1);
		}
		return ans;
	}
	
	public boolean findVal(int v) {
		int indBuck = findBuck(v);
		Obj now = bucketArray.get(indBuck);
        Obj prev = null;
		
		// find v in hash table
        while (now != null) {
            if (now.value == v) {
                break;
			}
 
            prev = now;
            now = now.next;
        }
		
		// if v is not found: return 
        if (now == null) {
            return false;
		}
		
		// if v is found: 
		return true;
	}
	
	public boolean remove(int v) {

        int indBuck = findBuck(v);
        Obj now = bucketArray.get(indBuck);
        Obj prev = null;
		
		// find v in hash table
        while (now != null) {
            if (now.value == v) {
                break;
			}
 
            prev = now;
            now = now.next;
        }
		
		// if v is not found: return 
        if (now == null) {
            return false;
		}
		
		// if v is found: 
        numObj--;
 
        if (prev != null) {
			prev.next = now.next;
		}
        else {
            bucketArray.set(indBuck, now.next);
		}
		return true;
    }
	
	public void add(int v) {
		// if v is found: return
        if (findVal(v)) {
			return;
		}
		
        // if v is not found: add to the head of the bucket
        numObj++;
		
		int indBuck = findBuck(v);
		Obj newObj = new Obj(v);
        Obj head = bucketArray.get(indBuck);
        newObj.next = head;
        bucketArray.set(indBuck, newObj);

    }
}

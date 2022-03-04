import java.util.Scanner;

public class Q5 {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Operand:");
        String Op = myObj.nextLine();
        while (Op.compareTo("quit") != 0) {
			
			if (Op.compareTo("prime") == 0) {
				String In1 = myObj.nextLine();
				int num = Integer.parseInt(In1);
				int pr = 1;
				for (int i = 2; i < num; i++) {
					if (num%i == 0) {
						pr = 0;
					}
				}
				
				if (pr == 1) {
					System.out.println(String.valueOf(Integer.parseInt(In1)) + " is prime.");
				}
				else {
					System.out.println(String.valueOf(Integer.parseInt(In1)) + " is NOT prime.");
				}
			}
			
			else {
				String In1 = myObj.nextLine();
				String In2 = myObj.nextLine();
				if (Op.compareTo("add") == 0)
					System.out.println("Result:" + String.valueOf(Integer.parseInt(In1) + Integer.parseInt(In2)));
				else if (Op.compareTo("subtract") == 0)
					System.out.println("Result:" + String.valueOf(Integer.parseInt(In1) - Integer.parseInt(In2)));
				else if (Op.compareTo("multiply") == 0)
					System.out.println("Result:" + String.valueOf(Integer.parseInt(In1) * Integer.parseInt(In2)));
				else if (Op.compareTo("divide") == 0 && Integer.parseInt(In2)!=0)
					System.out.println("Result:" + String.valueOf(Integer.parseInt(In1) / Integer.parseInt(In2)));
				else {
					System.out.println("invalid input");
				}
			}
			
            System.out.println("Operand:");
            Op = myObj.nextLine();
        }
        myObj.close();
    }
}
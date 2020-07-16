import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double m = scan.nextInt();
        double p = scan.nextDouble() / 100 + 1;
        int k = scan.nextInt();
        int counter = 0;
        while(m < k){
            m *= p;
            counter++;
        }
        System.out.println(counter);
    }
}
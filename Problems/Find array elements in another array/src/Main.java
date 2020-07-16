import java.util.Arrays;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] first = new int[n];
    for(int i = 0; i < n; i++){
      first[i] = scanner.nextInt();
    }
    int k = scanner.nextInt();
    for(int i = 0, val; i < k; i++){
      val = Arrays.binarySearch(first, scanner.nextInt());
      System.out.print((val < 0? -1: val + 1) + " ");
    }
  }
}
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] mas = new int[n];
        for(int i = 0; i < n; i++){
            mas[i] = scanner.nextInt();
        }
        System.out.println(isFixed(mas));
    }

    private static boolean isFixed(int[] mas){
        for(int i = 0; i < mas.length; i++){
            if(i == mas[i]){
                return true;
            }
        }
        return false;
    }
}
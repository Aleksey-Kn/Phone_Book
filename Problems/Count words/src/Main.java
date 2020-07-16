import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int counter = 0;
        int now = reader.read();
        boolean flag = false;
        while (now != -1){
            if((char)now == ' '){
                if(flag) {
                    counter++;
                    flag = false;
                }
            }
            else {
                flag = true;
            }
            now = reader.read();
        }
        System.out.println(counter + (flag? 1: 0));
        reader.close();
    }
}
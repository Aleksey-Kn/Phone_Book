import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder stringBuilder = new StringBuilder();
        int now = reader.read();
        while (now != -1){
            stringBuilder.append((char)now);
            now = reader.read();
        }
        System.out.println(stringBuilder.reverse().toString());
        reader.close();
    }
}
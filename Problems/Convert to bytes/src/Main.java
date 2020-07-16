import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        byte b = (byte)inputStream.read();
        while (b != -1){
            System.out.print(b);
            b = (byte)inputStream.read();
        }
    }
}
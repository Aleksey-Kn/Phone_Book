import java.util.Scanner;

public class Main {
    private static class TableEntry<T> {
        private final int key;
        private final T value;
        private boolean removed;

        public TableEntry(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public void remove() {
             removed = true;   
        }

        public boolean isRemoved() {
             return removed;   
        }
    }

    private static class HashTable<T> {
        private int size;
        private TableEntry[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        public boolean put(int key, T value) {
            int id = findKey(key);
            if(id != -1){
                table[id] = new TableEntry(key, value);
                return true;
            }
            return false;
        }

        public T get(int key) {
            int id = findKey(key);
            if(id != -1 && table[id] != null && !table[id].isRemoved()){
                return (T)table[id].value;
            }
            return null;
        }

        public void remove(int key) {
            int id = findKey(key);
            if(id != -1 && table[id] != null){
                table[id].remove();
            }
        }

        private int findKey(int key) {
            int hash = key % size;
            for(int i = 1; !(table[hash] == null || table[hash].isRemoved() || table[hash].key == key); i *= 2){
                hash = (hash + i) % size;
                if(hash == key % size){
                    return -1;
                }
            }
            return hash;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        HashTable<String> table = new HashTable<>(n);
        for(int i = 0; i < n; i++){
            switch (scanner.next()){
                case "put":
                    table.put(scanner.nextInt(), scanner.next());
                    break;
                case "get":
                    String s = table.get(scanner.nextInt());
                    System.out.println(s == null? -1: s);
                    break;
                case "remove":
                    table.remove(scanner.nextInt());
                    break;
            }
        }
    }
}
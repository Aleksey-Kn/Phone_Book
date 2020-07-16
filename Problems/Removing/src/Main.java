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
            if(id == -1){
                rehash();
                id = findKey(key);
            }
            table[id] = new TableEntry(key, value);
            return true;
        }

        public T get(int key) {
            int idx = findKey(key);

            if (idx == -1 || table[idx] == null) {
                return null;
            }

            return (T) table[idx].getValue();
        }

        private int findKey(int key) {
            int hash = key % size;

            while (!(table[hash] == null || table[hash].getKey() == key)) {
                hash = (hash + 1) % size;

                if (hash == key % size) {
                    return -1;
                }
            }

            return hash;
        }

        public void remove(int key) {
            int id = findKey(key);
            if(id != -1 && table[id] != null){
                table[id].remove();
            }
        }

        private void rehash() {
            size *= 2;
            TableEntry[] newTable = new TableEntry[size];
            TableEntry[] old = table;
            table = newTable;
            for(int i = 0, id; i < size / 2; i++){
                id = findKey(old[i].key);
                table[id] = old[i];
            }
        }

        @Override
        public String toString() {
            StringBuilder tableStringBuilder = new StringBuilder();

            for (int i = 0; i < table.length; i++) {
                if (table[i] == null) {
                    tableStringBuilder.append(i + ": null");
                } else {
                    tableStringBuilder.append(i + ": key=" + table[i].getKey()
                                                + ", value=" + table[i].getValue()
                                                + ", removed=" + table[i].isRemoved());
                }

                if (i < table.length - 1) {
                    tableStringBuilder.append("\n");
                }
            }

            return tableStringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashTable<String> table = new HashTable<>(5);
        int n = scanner.nextInt();
        int t = scanner.nextInt();
        for(int i = 0; i < n; i++){
            table.put(scanner.nextInt(), scanner.next());
        }
        for(int i = 0; i < t; i++){
            table.remove(scanner.nextInt());
        }
        System.out.println(table.toString());
    }
}
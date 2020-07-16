package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File find = new File("C:\\Stud\\find.txt");
            File directory = new File("C:\\Stud\\directory.txt");
            List<String> names_find = new ArrayList<>();
            List<String> names_directory = new ArrayList<>();
            int find_name_count = 0;

            try (Scanner scan = new Scanner(find)) {
                while (scan.hasNextLine()) {
                    names_find.add(scan.nextLine());
                    find_name_count++;
                }
            }

            try(Scanner scan = new Scanner(directory)) {
                String[] names;
                while(scan.hasNextLine()) {
                    names = scan.nextLine().split(" ");
                    names_directory.add(names.length != 2 ? names[1] + " " + names[2] : names[1]);
                }
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
            finally{ System.out.println("Start searching...");}

            int count = 0;
            long start_time = System.currentTimeMillis();
            for(String name : names_find) {
                for(String dir : names_directory) {
                    if (name.equals(dir)) {
                        count++;
                    }
                }
            }
            long end_time = System.currentTimeMillis();
            long timeTaken = end_time - start_time;

            System.out.printf("Found %s / %s entries. Time taken %d min. %d sec. %s ms. \n", count, find_name_count,
                    timeTaken / 60000, timeTaken / 1000 - (timeTaken / 60000) * 60,
                    timeTaken % 1000);

            String[] arraysDirectory = new String[names_directory.size()];
            names_directory.toArray(arraysDirectory);

            System.out.println("Start searching (bubble sort + jump search)...");
            long timeOfSort = bubbleSort(names_directory, timeTaken * 10);
            long timeOfSearch = 0;
            long all;
            count = 0;
            if(timeOfSort > timeTaken * 10){
                long startOfSearch = System.currentTimeMillis();
                for(String name : names_find) {
                    for(String dir : names_directory) {
                        if (name.equals(dir)) {
                            count++;
                        }
                    }
                }
                timeOfSearch = System.currentTimeMillis() - startOfSearch;
                //timeOfSearch += 2387;
            }
            else{
                for(String target: names_find){
                    timeOfSearch += search(names_directory, target, 0, names_directory.size() - 1);
                    count++;
                }
            }
            all = timeOfSearch + timeOfSort;
            System.out.printf("Found %s / %s entries. Time taken %d min. %d sec. %s ms.\n", count, find_name_count,
                    all / 60000, all / 1000 - (all / 60000) * 60,
                    all % 1000);
            System.out.printf("Sorting time: %d min. %d sec. %d ms. %s \n", timeOfSort / 60000,
                    timeOfSort / 1000 - (timeOfSort / 60000) * 60, timeOfSort % 1000, (timeOfSort > timeTaken * 10? "- STOPPED, moved to linear search": ""));
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n", timeOfSearch / 60000,
                    timeOfSearch / 1000 - (timeOfSearch / 60000) * 60, timeOfSearch % 1000);

            System.out.println("Start searching (quick sort + binary search)...");
            timeOfSort = quickSort(arraysDirectory, 0, names_directory.size() - 1);
            count = 0;
            timeOfSearch = System.currentTimeMillis();
            for(String target: names_find){
                if(Arrays.binarySearch(arraysDirectory, target) != -1){
                    count++;
                }
            }
            timeOfSearch = System.currentTimeMillis() - timeOfSearch;
            all = timeOfSearch + timeOfSort;
            System.out.printf("Found %s / %s entries. Time taken %d min. %d sec. %s ms.\n", count, find_name_count,
                    all / 60000, all / 1000 - (all / 60000) * 60,
                    all % 1000);
            System.out.printf("Sorting time: %d min. %d sec. %d ms. \n", timeOfSort / 60000,
                    timeOfSort / 1000 - (timeOfSort / 60000) * 60, timeOfSort % 1000);
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n", timeOfSearch / 60000,
                    timeOfSearch / 1000 - (timeOfSearch / 60000) * 60, timeOfSearch % 1000);

            System.out.println("Start searching (hash table)...");
            timeOfSort = System.currentTimeMillis();
            HashTable table = new HashTable(5000000);
            for(int i = 0; i < arraysDirectory.length; i++){
                table.put(arraysDirectory[i], i);
            }
            timeOfSort = System.currentTimeMillis() - timeOfSort;
            count = 0;
            timeOfSearch = System.currentTimeMillis();
            for(String now: names_find){
                if(table.get(now) != -1){
                    count++;
                }
            }
            timeOfSearch = System.currentTimeMillis() - timeOfSearch;
            all = timeOfSearch + timeOfSort;
            System.out.printf("Found %s / %s entries. Time taken %d min. %d sec. %s ms.\n", count, find_name_count,
                    all / 60000, all / 1000 - (all / 60000) * 60,
                    all % 1000);
            System.out.printf("Creating time: %d min. %d sec. %d ms. \n", timeOfSort / 60000,
                    timeOfSort / 1000 - (timeOfSort / 60000) * 60, timeOfSort % 1000);
            System.out.printf("Searching time: %d min. %d sec. %d ms.\n", timeOfSearch / 60000,
                    timeOfSearch / 1000 - (timeOfSearch / 60000) * 60, timeOfSearch % 1000);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static long bubbleSort(List<String> list, long maxTime){
        long currrentTime = System.currentTimeMillis();
        for (int i = list.size() - 1; i > 0 && System.currentTimeMillis() - currrentTime <  maxTime; i--){
            for(int j = 0; j < i; j++){
                if(list.get(j).compareTo(list.get(j + 1)) > 0){
                    String temp = list.get(j);
                    list.set(j, list.get((j + 1)));
                    list.set(j + 1, temp);
                }
            }
        }
        return System.currentTimeMillis() - currrentTime;
    }

    private static long search(List<String> list, String target, int startIndex, int endIndex){
        if(startIndex == endIndex || target.compareTo(list.get(0)) < 0 || target.compareTo(list.get(list.size() - 1)) > 0){
            return 0;
        }
        long startTime = System.currentTimeMillis();
        int step = (int)Math.sqrt(endIndex - startIndex);
        for(int now = 0, prew = 0; true; prew = now, now = Math.min(now + step, list.size() - 1)){
            if(list.get(now).equals(target)){
                return System.currentTimeMillis() - startTime;
            }
            if(list.get(now).compareTo(target) > 0){
                return (System.currentTimeMillis() - startTime) + search(list, target, prew + 1, now - 1);
            }
        }
    }

    private static long quickSort(String[] list, int st, int end){
        long start = System.currentTimeMillis();
        int i = st, j = end;
        String op = list[(st + end) / 2];
        String temp;
        while (i <= j){
            while(list[i].compareTo(op) < 0){
                i++;
            }
            while (list[j].compareTo(op) > 0){
                j--;
            }
            if(i <= j){
                temp = list[i];
                list[i] = list[j];
                list[j] = temp;
                i++;
                j--;
            }
        }
        if(st < j) {
            quickSort(list, st, j);
        }
        if(i < end) {
            quickSort(list, i, end);
        }
        return System.currentTimeMillis() - start;
    }
}

class TableEntry<T> {
    private final int key;
    private final T value;

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
}

class HashTable{
    private int size;
    private TableEntry[] table;

    private class TableEntry {
        private final String key;
        private final int value;

        public TableEntry(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashTable(int size) {
        this.size = size;
        table = new TableEntry[size];
    }

    public boolean put(String key, int value) {
        int id = findKey(key);
        if(id == -1){
            rehash();
            id = findKey(key);
        }
        table[id] = new TableEntry(key, value);
        return true;
    }

    public int get(String key) {
        int idx = findKey(key);

        if (idx == -1 || table[idx] == null) {
            return -1;
        }

        return table[idx].value;
    }

    private int findKey(String key) {
        int hash = Math.abs(key.hashCode() % size);

        while(!(table[hash] == null || table[hash].key.equals(key))) {
            hash = (hash + 1) % size;

            if (hash == Math.abs(key.hashCode() % size)) {
                return -1;
            }
        }

        return hash;
    }

    private void rehash() {
        size *= 2;
        TableEntry[] newTable = new TableEntry[size];
        TableEntry[] old = table;
        table = newTable;
        for(int i = 0, id; i < size / 2; i++) {
            id = findKey(old[i].key);
            newTable[id] = old[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder tableStringBuilder = new StringBuilder();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                tableStringBuilder.append(i + ": null");
            } else {
                tableStringBuilder.append(i + ": key=" + table[i].key
                        + ", value=" + table[i].value);
            }

            if (i < table.length - 1) {
                tableStringBuilder.append("\n");
            }
        }

        return tableStringBuilder.toString();
    }
}
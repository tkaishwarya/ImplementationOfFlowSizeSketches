import java.io.BufferedReader;
import java.io.FileReader;


public class Parse {


    private static int parseString(String object) {
        int map_val = object.hashCode() % Integer.MAX_VALUE;
        map_val = ~map_val + (map_val << 15);
        map_val = map_val ^ (map_val >>> 12);
        map_val = map_val + (map_val << 2);
        map_val = map_val ^ (map_val >>> 4);
        map_val = map_val * 2057;
        map_val = map_val ^ (map_val >>> 16);
        return map_val;
    }

    private static int[][] InputFileParser() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\DELL\\eclipse-workspace\\project3.txt"))) {
            int length = Integer.parseInt(br.readLine());
            int[][] storage = new int[length][2];
            for (int i = 0; i < length; i++) {
                String[] chars = br.readLine().split("\\s+");
                storage[i][0] = parseString(chars[0]);
                storage[i][1] = Integer.parseInt(chars[1]);
            }
            return storage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        int k_counters = 3;
        int counter_length = 3000;
        int[][] storage = InputFileParser();

        if (storage != null) {
            CountMin.callback(k_counters, counter_length, storage);
            CounterSketch.callback(k_counters, counter_length, storage);
            ActiveCounter.callback(k_counters);

        }
    }
}
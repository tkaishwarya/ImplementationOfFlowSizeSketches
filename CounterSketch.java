
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CounterSketch {
    public static final Random r = new Random(Instant.now().toEpochMilli());



    public static int findAverage(int counter_length, int[][] filter_arr, int[] hash_arr, int[] storage) {
        long k1 = storage[0];
        long shift;
        int[] prod = new int[hash_arr.length];
        for (int i = 0; i < hash_arr.length; i++) {
            shift = ((k1 ^ hash_arr[i]) >> 31) & 1;
            int index = (int) Math.abs((k1 ^ hash_arr[i]) % counter_length);
            prod[i] = filter_arr[i][index];
            if (shift == 0L) {
                prod[i] = -prod[i];
            }
        }
        Arrays.sort(prod);
        return prod[1];
    }

    public static void callback(int k_counters, int counter_length, int[][] storage) {
        int[][] filter_arr = new int[k_counters][counter_length];
        int[] hash_arr = new int[k_counters];
        for (int i = 0; i < k_counters; i++) {
            hash_arr[i] = r.nextInt(Integer.MAX_VALUE);
        }
        for (int[] i : storage) {
            //CounterSketch.enter(counter_length, filter_arr, hash_arr, i);
            long k1 = i[0];
            long k2 = i[1];
            long shift;
            for (int j = 0; j < hash_arr.length; j++) {
                shift = ((k1 ^ hash_arr[j]) >> 31) & 1;
                int index = (int) Math.abs((k1 ^ hash_arr[j]) % counter_length);
                if (shift == 0L) {
                    filter_arr[j][index] -= k2;
                } else {
                    filter_arr[j][index] += k2;
                }
            }
        }
        List<long[]> placeResult = new ArrayList<>();
        int e = 0;
        for (int[] i : storage) {
            int median = CounterSketch.findAverage(counter_length, filter_arr, hash_arr, i);
            e += Math.abs((median - i[1]));
            placeResult.add(new long[]{median, i[0], i[1]});
        }
        placeResult.sort((a, b) -> (int) (b[0] - a[0]));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\CounterSketch.txt"))) {
            bw.write("error rates: " + (e / storage.length));
            bw.write("\n");
            bw.write("original, flowids, expected" + "\n");
            for (long[] val : placeResult) {
                bw.write(val[0] + " " + val[1] + " " + val[2] + "\n");
            }
            bw.write("\n");
            bw.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
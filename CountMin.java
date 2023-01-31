import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountMin {

    public static final Random r = new Random(Instant.now().toEpochMilli());

    public static int findMinimum(int counter_length, int[][] filter_array, int[] hash_arr, int[] storage) {
        long k1 = storage[0];
        int small = Integer.MAX_VALUE;
        for (int i = 0; i < hash_arr.length; i++) {
            int k2 = (int) Math.abs((k1 ^ hash_arr[i]) % counter_length);
            if (small > filter_array[i][k2]) {
                small = filter_array[i][k2];
            }
        }
        return small;
    }

    public static void callback(int k_counters, int counter_length, int[][] storage) {
        int[][] filter_array = new int[k_counters][counter_length];
        int[] hash_arr = new int[k_counters];
        for (int i = 0; i < k_counters; i++) {
        	
            hash_arr[i] = r.nextInt(Integer.MAX_VALUE);
        }
        for (int[] val : storage) {
        	long k1 = val[0];
            long k2 = val[1];
            for (int i = 0; i < hash_arr.length; i++) {
                int k3 = (int) Math.abs((k1 ^ hash_arr[i]) % counter_length);
                filter_array[i][k3] += k2;
            }
        }
        List<long[]> placeResult = new ArrayList<>();
        int e = 0;
        for (int[] val : storage) {
            int small = CountMin.findMinimum(counter_length, filter_array, hash_arr, val);
            e += Math.abs(small - val[1]);
            placeResult.add(new long[]{small, val[0], val[1]});
        }
        placeResult.sort((a1, a2) -> (int) (a2[0] - a1[0]));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\CountMinOutput.txt"))) {
            bw.write("error rates : " + (e / storage.length));
            bw.write("\n");
            bw.write("original, flowids, expected" + "\n");
            for (long[] value : placeResult) {
                bw.write(value[0] + " " + value[1] + " " + value[2] + "\n");
            }
            bw.write("\n");
            bw.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class ActiveCounter {
    public static final Random r = new Random(Instant.now().toEpochMilli());

    public static void enter(int[] c, int current, int prev, int exp) {
        if (current > prev) {
            enter(c, 0, exp, exp);
            for (int k1 = prev; k1 > exp - 1; k1--) {
                c[k1] = (k1 == prev) ? 1 : 0;
            }
        } else {
            if (c[current] == 1) {
                c[current] = 0;
                enter(c, current + 1, prev, exp);
            } else if (c[current] == 0) {
                c[current] = 1;
            }
        }
    }


    public static void callback(int n) {
        int[] c = new int[32];
        int result, product = 1;
        for (int i = 0; i < n; i++) {
            if (r.nextInt(product) == 0) {
                enter(c, 16, 31, 16);
            }
            long result1;
            StringBuilder sb = new StringBuilder();
            for (int j = 15; j >= 0; j--)
                sb.insert(16 - j - 1, c[j]);
            result1 = Integer.parseInt(sb.toString(), 2);
            result = (int) result1;
            product = (int) Math.ceil(Math.pow(2, result));
        }

        // converting binary to decimal for output
        long res;
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 15; i >= 0; i--)
            sb1.insert(15 - i, c[i]);
        for (int i = c.length - 1; i >= 16; i--)
            sb2.insert(c.length - 1 - i, c[i]);
        res = Integer.parseInt(sb2.toString(), 2);
        res *= Math.pow(2, Integer.parseInt(new String(sb1), 2));


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\ActiveCounters.txt"))) {
            bw.write("counter_res: " + res);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
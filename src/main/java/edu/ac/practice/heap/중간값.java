package edu.ac.practice.heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author noah kim
 * @date 2024/02/24
 * @link
 * @requirement
 * @summary
 * @input
 * @output
 * @time_complex
 * @perf
 */
public class 중간값 {
    private static final StringBuffer sb = new StringBuffer();
    private static final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder()), minHeap = new PriorityQueue<>();
    private static final int LIMIT = 20171109;
    private static int TC, N, A, X, Y;
    private static long ans;
    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            while ((c = read()) <= 32);
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0)
                    buffer[0] = -1;
            }
            return buffer[index++];
        }
    }

    public static void main(String[] args) throws Exception {
        Reader reader = new Reader();
        TC = reader.nextInt();

        for (int t = 1; t <= TC; t++) {
            ans = 0;

            minHeap.clear();
            maxHeap.clear();

            N = reader.nextInt();
            A = reader.nextInt();

            minHeap.offer(A);

            for (int i = 0; i < N; i++) {
                X = reader.nextInt();
                Y = reader.nextInt();

                minHeap.offer(X);
                maxHeap.offer(Y);

                while (minHeap.peek() < maxHeap.peek()) {
                    int small = minHeap.poll();
                    int big = maxHeap.poll();

                    minHeap.offer(big);
                    maxHeap.offer(small);
                }

                ans += minHeap.peek();
            }

            sb.append("#"+t+" ").append(ans % LIMIT).append("\n");
        }

        System.out.println(sb);
    }
}

package edu.ac.practice.dp;

import java.io.*;
import java.util.Arrays;

public class 구간_합 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static int[] input;
    private static int TC;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int src = input[0];
            int dest = input[1];

            sb.append("#").append(t).append(" ").append(sum1(src, dest))
                    .append("\n");
        }

        System.out.println(sb);
    }

    private static long sum1(int src, int dest) {
        return 0;
    }
}

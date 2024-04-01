package edu.ac.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class 조합 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static long[] factorial = new long[1000001];
    private static int[] tmp;
    private static final int MOD = 1234567891;
    private static int TC, N, R;

    public static void main(String[] args) throws IOException {
        factorial[0] = 1;
        for (int i = 1; i <= 1000000; i++) {
            factorial[i] = factorial[i-1] * i % MOD;
        }

        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            N = tmp[0];
            R = tmp[1];

            long c1 = factorial[N];
            long c2 = (factorial[N-R]*factorial[R]) % MOD;
            long c3 = cal(c2, MOD-2);

            sb.append("#").append(" ").append(t).append(c1*c3 % MOD);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static long cal(long n, long k) {
        if (k == 1) return n;

        long x = cal(n, k/2) % MOD;
        if (k % 2 == 0) return x*x% MOD;

        return (((x*x)% MOD)*n)% MOD;
    }
}

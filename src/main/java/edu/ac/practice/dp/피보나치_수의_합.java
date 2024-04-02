package edu.ac.practice.dp;

import java.io.*;
import java.util.*;

public class 피보나치_수의_합 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Map<Long, Long> map = new TreeMap<>();
    private static final long MOD = 1_000_000_000;
    private static long[] input;
    private static long a, b;

    public static void main(String[] args) throws IOException {
        map.put(0L, 0L);
        map.put(1L, 1L);

        input = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        a = input[0];
        b = input[1];

        System.out.println((fibSum(b) - fibSum(a-1) + MOD)%MOD);
    }

    private static long fib(long n) {
        if (map.containsKey(n)) return map.get(n);

        long k = n/2;
        long value = 0;

        if (n%2==0) {
            value = (fib(k)*(2*fib(k-1) + fib(k))) % MOD;
        } else {
            value = (fib(k)*fib(k) + fib(k+1)*fib(k+1)) % MOD;
        }

        map.put(n, value);

        return value;
    }

    private static long fibSum(long n) {
        return (fib(n+2) - 1 + MOD) % MOD;
    }
}

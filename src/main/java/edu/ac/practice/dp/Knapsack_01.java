package edu.ac.practice.dp;

/**
 * @author noah kim
 * @date 2024/02/24
 * @link
 * @requirement
 * @summary
 * @input
 * @output
 * @time_complex
 * @perf 25,380kb / 145ms
 */
public class Knapsack_01 {
    private static StringBuffer sb = new StringBuffer();
    private static int[][] dp; // r물건까지 사용해서 c부피까지 채울 수 있는 최대 가치
    private static int[] costs, volumes;
    private static int TC, N, K, V, C;
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
            N = reader.nextInt();
            K = reader.nextInt();

            costs = new int[N+1];
            volumes = new int[N+1];

            dp = new int[N+1][K+1];

            for (int i = 1; i <= N; i++) {
                V = reader.nextInt();
                C = reader.nextInt();
                costs[i] = C;
                volumes[i] = V;
            }

            for (int item = 1; item <= N; item++) {
                for (int volume = 1; volume <= K; volume++) {
                    int V = volumes[item], C = costs[item];

                    dp[item][volume] = volume>=V ? Math.max(dp[item-1][volume], dp[item-1][volume-V]+C) : dp[item-1][volume];
                }
            }

            sb.append("#"+t+" ").append(dp[N][K]).append("\n");
        }

        System.out.println(sb);
    }
}

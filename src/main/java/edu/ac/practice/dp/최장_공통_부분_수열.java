package edu.ac.practice.dp;

import java.io.*;
import java.util.*;

public class 최장_공통_부분_수열 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static StringTokenizer st;
    private static char[] a, b;
    private static int[][] dp;
    private static int TC;

    public static void main(String[] args) throws Exception {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            st = new StringTokenizer(br.readLine());
            a = st.nextToken().toCharArray();
            b = st.nextToken().toCharArray();

            dp = new int[a.length+1][b.length+1];

            for (int i = 0; i <= a.length; i++) {
                for (int j = 0; j <= b.length; j++) {
                    if (i == 0 || j == 0) dp[i][j] = 0;
                    else if (a[i-1] == b[j-1]) dp[i][j] = dp[i-1][j-1]+1;
                    else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }

            sb.append("#"+t+" ").append(dp[a.length][b.length]).append("\n");
        }

        System.out.println(sb);
    }
}

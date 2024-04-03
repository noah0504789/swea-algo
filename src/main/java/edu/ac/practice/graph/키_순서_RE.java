package edu.ac.practice.graph;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-04-03
 * @link
 * @requirement
 * @keyword
 * @input
 * @output
 * @time_complex
 * @perf
 */
public class 키_순서_RE {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static StringTokenizer st;
    private static boolean[][] check;
    private static int[] cnt;
    private static int TC, N, M, a, b, ans;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());
            check = new boolean[N][N];
            cnt = new int[N];
            ans = 0;

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken())-1;
                b = Integer.parseInt(st.nextToken())-1;
                check[a][b] = true;
            }

            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (check[i][k] && check[k][j]) check[i][j] = true;
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (check[i][j] || check[j][i]) cnt[i]++;
                }
            }

            for (int c : cnt) {
                if (c == N-1) ans++;
            }

            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}

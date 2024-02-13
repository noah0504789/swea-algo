package edu.ac.practice.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/09
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?contestProbId=AWT-lPB6dHUDFAVT&solveclubId=AY0LFFoqrIIDFAXz&problemBoxTitle=0205%EC%A3%BC&problemBoxCnt=6&probBoxId=AY13IwlKMEcDFAWX
 * @reference https://fl0wering.tistory.com/5
 * @requirement 정해진 칼로리 이하의 햄버거의 조합 중, 가장 맛있는 햄버거의 맛을 출력하라.
 * @summary
    [햄버거]
    - 맛: 조합된 재료들의 맛의 합

    [재료]
    - 중복: X
    - 맛
    - 칼로리
 * @input TC: input
    - N: 재료 수(1<=N<=20), L: 제한 칼로리(1<=L<=10_000)
    - T: 맛(1<=T<=1_000), C: 칼로리(1<=C<=1_000)
 * @output
 * @time_complex
 * @perf
 */
public class 햄버거_다이어트_T2 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static StringTokenizer st;
    private static int[][] dp;
    private static int[] tastes, calories;
    private static int T, N, L, taste, calory, ans;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            tastes = new int[N];
            calories = new int[N];

            ans = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                taste = Integer.parseInt(st.nextToken());
                calory = Integer.parseInt(st.nextToken());

                tastes[i] = taste;
                calories[i] = calory;
            }

            dp = new int[N+1][L+1];

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= L; j++) {
                    if (calories[i-1] > j) dp[i][j] = dp[i-1][j];
                    else dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-calories[i-1]] + tastes[i-1]);
                }
            }

            sb.append("#"+t+" "+dp[N][L]).append("\n");

//            dfs(0, 0, 0);

//            sb.append("#"+t+" "+ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void dfs(int idx, int cSum, int tSum) {
        if (cSum > L) return;

        if (idx == N) {
            if (tSum > ans) ans = tSum;
            return;
        }

        dfs(idx+1, cSum+calories[idx], tSum+tastes[idx]);
        dfs(idx+1, cSum, tSum);
    }
}

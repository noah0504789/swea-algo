package edu.ac.practice.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author noah kim
 * @date 2024-02-14
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV7GKs06AU0DFAXB&probBoxId=AY2gBgM6OSIDFAXh&type=PROBLEM&problemBoxTitle=0212%EC%A3%BC&problemBoxCnt=2
 * @requirement N이 주어졌을 때, 퀸을 놓는 방법의 수를 구하는 프로그램을 작성하시오.
 * @keyword
 * @input TC: input
    - N
 * @output
 * @time_complex O(N^2)
 * @perf 19,956 kb / 113 ms
 */
public class N_Queen {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static int[] queens;
    private static int T, N, ans;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            queens = new int[N];
            ans = 0;

            dfs(0);

            sb.append("#"+t+" ").append(ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void dfs(int depth) {
        if (depth == N) {
            ans++;
            return;
        }

        for (int i = 0; i < N; i++) {
            boolean prune = false;
            for (int j = depth-1; j >= 0; j--) {
                if (Math.abs(queens[j] - i) == depth - j || queens[j] == i) {
                    prune = true;
                    break;
                }
            }

            if (!prune) {
                queens[depth] = i;
                dfs(depth+1);
            }
        }
    }
}

package edu.ac.practice.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author noah kim
 * @date 2024/02/09
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AWIeUtVakTMDFAVH&probBoxId=AY13IwlKMEcDFAWX&type=PROBLEM&problemBoxTitle=0205%EC%A3%BC&problemBoxCnt=6
 * @requirement 두 개의 음식을 요리할 때, 음식 맛의 차이를 최소로 하는 값을 출력하라.
 * @summary
    [요리]
    - 구성: 전체 식재료들 중 절반 사용
    - 맛: 시너지들의 합

    [시너지]
    - 발생: 두 식재료를 함께 사용할 경우
    - 중복: X
    - 순서: O
 * @input TC: input
    - N: 식재료 수 (4<=N<=16)
    - S: 시너지 정보 (1<=S_i,j<=20_000)(N*N)
 * @output TC
    - 음식 맛의 차이를 최소로 하는 값
 * @time_complex O(N^2)
 * @perf 29,504 kb / 329 ms
 */
public class 요리사 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static int[][] synerge;
    private static boolean[] firstTeam;
    private static int T, N, ans;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            synerge = new int[N][];
            for (int r = 0; r < N; r++) {
                synerge[r] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            }

            firstTeam = new boolean[N];
            ans = Integer.MAX_VALUE;

            dfs(0, 0);

            sb.append("#" + t + " " + ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void dfs(int start, int depth) {
        if (depth == N/2) {
            int first = 0, second=0;
            for (int i = 0; i < N; i++) {
                for (int j = i; j < N; j++) {
                   if (firstTeam[i] && firstTeam[j]) first+= synerge[i][j] + synerge[j][i];
                   if (!firstTeam[i] && !firstTeam[j]) second+= synerge[i][j] + synerge[j][i];
                }
            }

            ans = Math.min(ans, Math.abs(first-second));
            return;
        }

        for (int i = start; i < N; i++) {
            firstTeam[i] = true;
            dfs(i+1, depth+1);
            firstTeam[i] = false;
        }
    }
}

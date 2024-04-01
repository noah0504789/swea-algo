package edu.ac.practice.simulation;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-04-01
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWzaq5KKk_ADFAVU
 * @requirement 모든 점들을 원점으로 이동시키는데 드는 최소횟수를 구하라.
 * @keyword
    [이동규칙]
    - 모든 점들이 이동
    - i번째 이동시에, 상하좌우로 i거리만큼 이동
 * @input TC: input
    - N(1<=N<=10)
    - 점들의 좌표(x,y)(-1000000000<=x,y<=1000000000)
 * @output
 * @time_complex
 * @perf
 */
public class 원점으로_집합 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final int IMPOSSIBLE = -1;
    private static int[] points, tmp;
    private static int TC, N, x, y, sum, max, cnt;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            points = new int[N];

            tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            x = tmp[0];
            y = tmp[1];
            max = points[0] = Math.abs(x) + Math.abs(y);

            boolean stop = false;
            for (int i = 1; i < N; i++) {
                tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                x = tmp[0];
                y = tmp[1];
                points[i] = Math.abs(x) + Math.abs(y);
                max = Math.max(max, points[i]);

                if (points[i]%2 != points[i-1]%2) stop = true;
            }

            if (stop) {
                cnt = IMPOSSIBLE;
            } else {
                sum = 0;
                cnt = 0;
                while (true) {
                    if (sum >= max && (sum-max)%2 == 0) break;
                    sum += ++cnt;
                }
            }

            sb.append("#").append(t).append(" ").append(cnt);
            sb.append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}

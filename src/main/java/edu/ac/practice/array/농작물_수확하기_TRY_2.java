package edu.ac.practice.array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author noah kim
 * @date 2024-01-30
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV7GLXqKAWYDFAXB&probBoxId=AY0LFFoqrIMDFAXz+&type=PROBLEM&problemBoxTitle=0129%EC%A3%BC&problemBoxCnt=4
 * @keyword_solution
 * @input
 * @output
 * @time_complex 37,924 kb 250 ms
 * @perf
 */

// 농작물의 수확물을 수확하여 얻는 수익을 리턴하라.
// 수확할 수 있는 농작물: 정사각형 마름모 모양으로 수확한다.

public class 농작물_수확하기_TRY_2 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N;
    static int[][] farm;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            makeFarm();

            System.out.printf("#%d %d\n", t, getValue());
        }
    }

    private static void makeFarm() throws IOException {
        farm = new int[N][];

        for (int r = 0; r < N; r++) {
            farm[r] = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
        }
    }

    private static int getValue() {
        int ans = 0;

        for (int r = 0; r < N; r++) {
            int r2 = N - 1 - r;
            int cIdx, offset = Math.abs(r - r2) / 2;
            cIdx = offset;

            for (int c = 0; c < N - (offset * 2); c++) {
                ans += farm[r][cIdx++];
            }
        }

        return ans;
    }
}

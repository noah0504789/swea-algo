package edu.ac.practice.bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author noah kim
 * @date 2024/02/03
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWBnFuhqxE8DFAWr
 * @reference https://blog.youhogeon.com/20
 * @keyword_solution
    Requirements: N일 동안 활동할 수 있는 경우의 수를 출력하라.

    [열쇠 소유자]
    - 첫날에 결정됨 -> A
    - 반드시 활동 참여
    - 어제 같이 활동했던 사람에게 전달받음

    [책임자]
    - 날마다 있음
    - 반드시 활동 참여
 * @input
    - TC
    - TC별: 문자열(i번쨰 날의 책임자)(>=10_000)
 * @output
    - TC별: 동아리 활동을 할 수 있는 경우의 수 % 1_000_000_007
 * @time_complex
 * @perf
 */
public class 동아리실_관리하기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final int A_NUM = 8, B_NUM = 4, C_NUM = 2, D_NUM = 1, MOD_VAL = 1_000_000_007;
    private static String str;
    private static int[][] cache;
    private static int T, len;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            str = br.readLine();
            len = str.length();
            cache = new int[len+1][16];
            for (int i = 0; i <= len; i++) {
                for (int j = 0; j < 16; j++) {
                    cache[i][j] = -1;
                }
            }

            sb.append("#" + t + " " + solve(0, A_NUM) + "\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static int solve(int adminIdx, int prev) {
        if (adminIdx>=len) return 0;
        if (cache[adminIdx][prev] != -1) return cache[adminIdx][prev];

        int admin = c2i(adminIdx), result = 0;

        for (int option=1; option<=16; option++) {
            if ((admin & option)>0 && (prev & option)>0) {
                if (adminIdx==len-1) result++;
                else result = (result + solve(adminIdx+1, option)) % MOD_VAL;
            }
        }

        cache[adminIdx][prev] = result;
        return result;
    }



    private static int c2i(int i) {
        char c = str.charAt(i);

        switch (c) {
            case 'A': return 8;
            case 'B': return 4;
            case 'C': return 2;
            default: return 1;
        }
    }
}

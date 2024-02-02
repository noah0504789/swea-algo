package edu.ac.practice.bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author noah kim
 * @date 2024/02/03
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV18_yw6I9MCFAZN
 * @keyword_solution
    Requirements : 최소 몇번 양을 세어야 양의 번호에서 모든 숫자(0~9)를 볼 수 있을 지 출력하라.

    [양 세기]
    - 방식 : 1번부터 N의 배수로 카운트하여 양을 셈
 * @input
    - N (1 <= N <= 10^6)
 * @output
    -테스트케이스 별 양을 센 횟수
 * @time_complex O()
 * @perf
 */

public class 새로운_불면증_치료법 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer(), lambSb = new StringBuffer();
    private static final int complete = 0b1111111111;
    private static int T, N, cur;
    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            cur = 0;

            sb.append("#" + t + " " + getLambCnt() * N + "\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static int getLambCnt() {
        int cnt = 0, curLamb;

        while (cur != complete) {
            curLamb = N * ++cnt;
            lambSb.append(curLamb);

            for (char c : lambSb.toString().toCharArray()) {
                int digit = c - '0';
                cur |= (1 << digit);
            }

            lambSb.setLength(0);
        }

        return cnt;
    }
}

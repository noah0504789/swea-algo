package edu.ac.practice.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/04
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV14uWl6AF0CFAYD&probBoxId=AY0LFFoqrIMDFAXz+&type=PROBLEM&problemBoxTitle=0129%EC%A3%BC&problemBoxCnt=8
 * @keyword_solution
    Requirements: 주어진 숫자를 사이클 처리할 때, 최종 결과값을 출력하라.

    [사이클]
    - 동작원리 (1~5 반복)
        1. 맨앞의 원소 1감소 후 맨뒤로 이동
        2. 맨앞의 원소 2감소 후 맨뒤로 이동
        3. 맨앞의 원소 3감소 후 맨뒤로 이동
        4. 맨앞의 원소 4감소 후 맨뒤로 이동
        5. 맨앞의 원소 5감소 후 맨뒤로 이동
    - 반복횟수: 사이클 중 맨 뒤 숫자가 0일 경우
 * @input TC(10)
    - TC 번호
    - 데이터(8개)
 * @output
    - TC별 암호값
 * @time_complex
 * @perf
 */
public class 암호생성기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer(), sb2 = new StringBuffer();
    private static final Deque<Integer> deque = new LinkedList<>();
    private static final int T = 10;
    private static final int digitSize = 8;
    private static final int maxCycleSize = 5;
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            br.readLine();
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < digitSize; i++) {
                deque.addLast(Integer.parseInt(st.nextToken()));
            }

            int delta = 1;
            while (deque.getLast() > 0) {
                deque.offerLast(deque.pollFirst()-delta);
                delta = (delta%maxCycleSize)+1;
            }

            deque.pollLast();
            deque.offer(0);

            while (deque.size() > 0) {
                sb2.append(deque.pollFirst() + " ");
            }

            sb.append("#" + t + " " + sb2 + "\n");
            sb2.setLength(0);
        }

        System.out.println(sb);
    }
}

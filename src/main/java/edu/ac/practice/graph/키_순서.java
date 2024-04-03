package edu.ac.practice.graph;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-04-03
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXQsLWKd5cDFAUo
 * @requirement 학생들 중, 키 순서를 알 수 있는 학생수를 출력하라.
 * @keyword
 * @input
 * @output
 * @time_complex
 * @perf
 */
public class 키_순서 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final Queue<Integer> queue = new ArrayDeque<>();
    private static List<List<Integer>> high = new ArrayList<>();
    private static List<List<Integer>> low = new ArrayList<>();
    private static StringTokenizer st;
    private static BitSet visBit = new BitSet();
    private static int TC, N, M, a, b;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());

            high.clear();
            low.clear();

            for (int i = 0; i <= N; i++) {
                high.add(new ArrayList<>());
                low.add(new ArrayList<>());
            }

            M = Integer.parseInt(br.readLine());

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());

                high.get(a).add(b);
                low.get(b).add(a);
            }

            sb.append("#").append(t).append(" ").append(rankCnt()).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static int rankCnt() {
        int cnt = 0;

        for (int i = 1; i <= N; i++) {
            if (highCnt(i) + lowCnt(i) == N-1) cnt++;
        }

        return cnt;
    }

    private static int highCnt(int start) {
        int cnt = 0;
        visBit.clear();

        for (int h : high.get(start)) {
            queue.offer(h);
        }

        while (!queue.isEmpty()) {
            int h = queue.poll();
            if (visBit.get(h)) continue;
            visBit.set(h);
            cnt++;

            for (int nh : high.get(h)) {
                queue.offer(nh);
            }
        }

        return cnt;
    }

    private static int lowCnt(int start) {
        int cnt = 0;
        visBit.clear();

        for (int l : low.get(start)) {
            queue.offer(l);
        }

        while (!queue.isEmpty()) {
            int l = queue.poll();
            if (visBit.get(l)) continue;
            visBit.set(l);
            cnt++;

            for (int nl : low.get(l)) {
                queue.offer(nl);
            }
        }

        return cnt;
    }
}

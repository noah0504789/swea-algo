package edu.ac.practice.simulation;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-04-03
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AWIeV9sKkcoDFAVH&probBoxId=AY6XxUxKOtUDFASa&type=PROBLEM&problemBoxTitle=0401%EC%A3%BC&problemBoxCnt=5
 * @requirement k번 자석을 회전시킨 후 획득하는 점수의 총 합을 출력하라.
 * @keyword

 * @input
 * @output
 * @time_complex
 * @perf
 */
public class 특이한자석 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final Wheel[] wheels = new Wheel[4];
    private static final int N = 0;
    private static final int S = 1;
    private static final int DIR_CLOCK = 1;
    private static final int DIR_COUNTERCLOCK = -1;
    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int N_POINT = 0;
    private static final int S_POINT_1 = 1;
    private static final int S_POINT_2 = 2;
    private static final int S_POINT_3 = 4;
    private static final int S_POINT_4 = 8;
    private static int TC, K, start, dir, point;

    static class Wheel {
        int idx, orderBit;

        public Wheel(int idx, int orderBit) {
            this.idx = idx;
            this.orderBit = orderBit;
        }

        private int getTwelveBit() {
            return orderBit & 1;
        }

        private int getThreeBit() {
            return orderBit & 1<<2;
        }

        private int getNineBit() {
            return orderBit & 1<<6;
        }

        public void rotateClockwise() {
            orderBit <<= 1;

            boolean msb = (orderBit & 1<<7) > 0;
            orderBit |= msb ? 1 : 0;
        }

        public void rotateCounterClockwise() {
            orderBit >>= 1;

            boolean lsb = (orderBit & 1) > 0;
            orderBit |= lsb ? 1<<7 : 0;
        }

        public boolean canRotatedWithLeft(Wheel left) {
            return left.getThreeBit() != this.getNineBit();
        }

        public boolean canRotatedWithRight(Wheel right) {
            return right.getNineBit() != this.getThreeBit();
        }

        public int getPoint() {
            int twelveBit = getTwelveBit();
            if (twelveBit == N_POINT) return 0;

            if (idx == 0) return S_POINT_1;
            else if (idx == 1) return S_POINT_2;
            else if (idx == 2) return S_POINT_3;
            else if (idx == 3) return S_POINT_4;

            return -1;
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            point = 0;

            K = Integer.parseInt(br.readLine());

            for (int i = 0; i < 4; i++) {
                String s = Arrays.stream(br.readLine().split(" ")).reduce((a, b) -> a + b).get();
                wheels[i] = new Wheel(i, Integer.parseInt(s, 10));
            }

            for (int i = 0; i < K; i++) {
                start = Integer.parseInt(br.readLine())-1;
                dir = Integer.parseInt(br.readLine());

                if (start == 0) {
                    dfs(start, dir, RIGHT);
                } else if (start == 3) {
                    dfs(start, dir, LEFT);
                } else {
                    bfs(start, dir);
                }
            }

            for (int i = 0; i < 4; i++) {
                point += wheels[i].getPoint();
            }

            sb.append("#").append(t).append(" ").append(point).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void dfs(int start, int dir, int delta) {
        if (start < 0 || start >= 4) return;
        Wheel cur = wheels[start];

        int nextIdx = start + delta;
        if (nextIdx < 0 || nextIdx >= 4) return;
        Wheel next = wheels[nextIdx];
        if (delta==RIGHT && cur.canRotatedWithRight(next) || delta==LEFT && cur.canRotatedWithLeft(next)) {
            dfs(nextIdx, dir, delta);
        }

        if (dir == DIR_CLOCK) cur.rotateClockwise();
        else if (dir == DIR_COUNTERCLOCK) cur.rotateCounterClockwise();
    }

    private static void bfs(int start, int dir) {
        if (start < 0 || start >= 4) return;
        Wheel cur = wheels[start];

        int prevIdx = start-1;
        if (prevIdx < 0 || prevIdx >= 4) return;
        Wheel prev = wheels[prevIdx];
        if (cur.canRotatedWithLeft(prev)) dfs(prevIdx, dir, LEFT);

        int nextIdx = start+1;
        if (nextIdx < 0 || nextIdx >= 4) return;
        Wheel next = wheels[nextIdx];
        if (cur.canRotatedWithRight(next)) dfs(nextIdx, dir, RIGHT);

        if (dir == DIR_CLOCK) cur.rotateClockwise();
        else if (dir == DIR_COUNTERCLOCK) cur.rotateCounterClockwise();
    }
}

package edu.ac.practice.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author noah kim
 * @date 2024-03-29
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo
 * @requirement 최대한 많은 벽돌을 부수었을 때, 남은 벽돌들의 수를 출력하라.
 * @keyword
 * @input
    - N(1<=N<=4)
    - W(2<=W<=12)
    - H(2<=H<=15)
 * @output
 * @time_complex
 * @perf
 */
public class 벽돌깨기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final Queue<int[]> queue = new ArrayDeque<>();
    private static final int[][] DIRS = {{0,1},{1,0},{-1,0},{0,-1}};
    private static long[] board, copy;
    private static int[] permCols, tmp;
    private static int TC, N, W, H, totCnt, ans;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            N = tmp[0];
            W = tmp[1];
            H = tmp[2];

            totCnt = 0;
            ans = H*W;

            board = new long[W];
            copy = new long[W];
            permCols = new int[N];

            for (int r = 0; r < H; r++) {
                tmp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                for (int c = 0; c < W; c++) {
                    int val = tmp[c];
                    if (val > 0) totCnt++;

                    set(r, c, val);
                    copy[c] = board[c];
                }
            }

            perm(0);

            sb.append("#").append(t).append(" ").append(ans);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static void perm(final int depth) {
        if (ans == 0) return;

        if (depth == N) {
            ans = Math.min(ans, totCnt-play());
            rollback();
            return;
        }

        for (int col = 0; col < W; col++) {
            permCols[depth] = col;
            perm(depth+1);
        }
    }

    private static int play() {
        int cnt = 0;
        for (int c : permCols) {
            cnt += fall(c);
            compact();
        }

        return cnt;
    }

    private static int fall(int c) {
        int r = 0;

        while (r < H && get(r, c) == 0) r++;

        if (r == H) return 0;

        if (get(r, c) == 1) {
            set(r, c, 0);
            return 1;
        }

        return spread(r, c);
    }

    private static int spread(int sr, int sc) {
        int cnt = 0;
        queue.offer(new int[] {sr, sc});

        while (!queue.isEmpty()) {
            int[] cp = queue.poll();
            int r = cp[0], c = cp[1], power = get(r, c);
            if (power > 0) {
                set(r, c, 0);
                cnt++;
            }

            for (int p = 1; p < power; p++) {
                for (int[] dir : DIRS) {
                    int nr = r+dir[0]*p, nc = c+dir[1]*p;
                    if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;

                    int val = get(nr, nc);
                    if (val == 1) {
                        set(nr, nc, 0);
                        cnt++;
                    } else if (val > 1) {
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }

        return cnt;
    }

    private static void compact() {
        for (int c = 0; c < W; c++) {
            int insRow = H-1;
            for (int r = H-1; r >= 0; r--) {
                int val = get(r, c);
                if (val == 0) continue;
                set(insRow, c, val);

                if (insRow-- == r) continue;
                set(r, c, 0);
            }
        }
    }

    private static int get(int r, int c) {
        return (int)((board[c] >>> (r<<2)) & 15L);
    }

    private static void set(int r, int c, long val) {
        int offset = r<<2;
        long mask = 15L << offset;

        board[c] &= ~mask;
        board[c] |= (val<<offset);
    }

    private static void rollback() {
        System.arraycopy(copy, 0, board, 0, W);
    }
}

package edu.ac.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
1
7
0 0 1 0 0 0 0
0 0 1 0 0 0 0
0 0 0 0 0 1 0
0 0 0 0 0 0 0
1 1 0 1 0 0 0
0 1 0 0 0 0 0
0 0 0 0 0 0 0
 */
public class 프로세서_연결하기_강사님 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final List<int[]> cores = new ArrayList<>();
    private static final int[] dr = {-1,1,0,0};
    private static final int[] dc = {0,0,-1,1};
    private static final int EMPTY = 0;
    private static final int CORE = 1;
    private static final int CABLE = 2;
    private static StringTokenizer st;
    private static int[][] board;
    private static int TC, N, minLength, maxCnt, totCnt;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            maxCnt = 0;
            minLength = Integer.MAX_VALUE;
            cores.clear();

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    board[r][c] = Integer.parseInt(st.nextToken());
                    if (r>0 && r<N-1 && c>0 && c<N-1 && board[r][c] == CORE) cores.add(new int[]{r, c});
                }
            }

            totCnt = cores.size();

            go(0, 0, 0);

            sb.append("#"+t+" ").append(minLength).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void go(int idx, int cnt, int sum) {
        if (cnt + (totCnt-idx) < maxCnt) return;

        if (idx == totCnt) {
            if (cnt == maxCnt && minLength > sum) {
                minLength = sum;
            } else if (cnt > maxCnt) {
                maxCnt = cnt;
                minLength = sum;
            }

            return;
        }

        int[] curC = cores.get(idx);
        int r = curC[0], c = curC[1];

        for (int d = 0; d < 4; d++) {
            if (isAvailable(r, c, d)) {
                int len = setState(r, c, d, CABLE);
                go(idx+1, cnt+1, sum+len);
                setState(r, c, d, EMPTY);
            }
        }

        go(idx+1, cnt, sum);
    }

    private static boolean isAvailable(int r, int c, int d) {
        int nr = r, nc = c;

        while (true) {
            nr += dr[d];
            nc += dc[d];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) return true;
            if (board[nr][nc] > 0) return false;
        }
    }

    private static int setState(int r, int c, int d, int s) {
        int nr = r, nc = c, cnt = 0;

        while (true) {
            nr += dr[d];
            nc += dc[d];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) break;
            board[nr][nc] = s;
            cnt++;
        }

        return cnt;
    }
}

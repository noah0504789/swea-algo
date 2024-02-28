package edu.ac.practice.simulation;

import java.io.*;
import java.util.*;

public class 프로세서_연결하기_GPT {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static List<int[]> cores;
    private static int[][] board;
    private static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    private static int TC, N, maxCnt, minLength, totCnt;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            cores = new ArrayList<>();
            maxCnt = 0;
            minLength = Integer.MAX_VALUE;
            totCnt = 0;

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] == 1 && (i != 0 && j != 0 && i != N-1 && j != N-1)) {
                        cores.add(new int[]{i, j});
                        totCnt++;
                    }
                }
            }

            dfs(0, 0, 0);

            sb.append("#" + t + " " + minLength).append("\n");
        }

        System.out.println(sb);
    }

    public static void dfs(int idx, int cnt, int length) {
        if (idx == cores.size()) {
            if (maxCnt < cnt) {
                maxCnt = cnt;
                minLength = length;
            } else if (maxCnt == cnt) {
                minLength = Math.min(minLength, length);
            }
            return;
        }

        int[] core = cores.get(idx);
        int x = core[0], y = core[1];

        for (int i = 0; i < 4; i++) {
            int markCnt = 0;
            int nx = x, ny = y;

            while (true) {
                nx += dx[i];
                ny += dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) break;
                if (board[nx][ny] == 1) {
                    markCnt = 0;
                    break;
                }
                markCnt++;
            }

            if (markCnt > 0) {
                int cx = x, cy = y;
                for (int j = 0; j < markCnt; j++) {
                    cx += dx[i];
                    cy += dy[i];
                    board[cx][cy] = 1;
                }

                dfs(idx+1, cnt+1, length + markCnt);

                cx = x;
                cy = y;
                for (int j = 0; j < markCnt; j++) {
                    cx += dx[i];
                    cy += dy[i];
                    board[cx][cy] = 0;
                }
            } else {
                dfs(idx+1, cnt, length);
            }
        }
    }
}


package edu.ac.practice.bfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author noah kim
 * @date 2024/02/12
 * @link https://swexpertacademy.com/main/code/codeBattle/problemDetail.do?contestProbId=AV5LwsHaD1MDFAXc&categoryId=AY1INdsqPvADFAWX&categoryType=BATTLE&battleMainPageIndex=1&&&&
 * @requirement 보드를 최소 몇번 클릭해야, 지뢰가 없는 모든 칸에 숫자가 표시될 것인지 출력하라.
 * @summary
    [보드]
    - 크기: R*C(정사각형)
    - 칸 클릭
        1. 지뢰
        2. 인접한 8칸의 지뢰 수 (해당 칸이 모두 0일 경우, 그 인접 칸들의 지뢰 수도 표기)
 * @input TC: input
    - N: 보드 크기(1<=N<=300)
    - 보드 정보 (*: 지뢰O, .: 지뢰X)
 * @output
 * @time_complex 34264kb / 178ms
 * @perf
 */
public class 파핑파핑_지뢰찾기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final Queue<int[]> queue = new LinkedList<>();
    private static final int[][] DIRS = {{0,1}, {0,-1}, {1,0}, {-1,0}, {-1,-1}, {-1,1}, {1,-1}, {1,1}};
    private static final char VISITED = 'V';
    private static final char MINE = '*';
    private static final char NON_MINE = '.';
    private static char[][] board;
    private static int T, N, clicks;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new char[N][N];
            clicks = 0;

            for (int r = 0; r < N; r++) {
                board[r] = br.readLine().toCharArray();
            }

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] == NON_MINE && board[r][c] != VISITED && !isMineArount(r, c)) {
                        queue.offer(new int[] {r, c});
                        bfs();
                        clicks++;
                    }
                }
            }

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (board[r][c] == NON_MINE && board[r][c] != VISITED) {
                        clicks++;
                    }
                }
            }

            sb.append("#"+t+" ")
              .append(clicks)
              .append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void bfs() {
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int r = point[0], c = point[1];
            board[r][c] = VISITED;

            for (int[] dir : DIRS) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (board[nr][nc] == MINE) continue;
                if (board[nr][nc] == VISITED) continue;
                board[nr][nc] = VISITED;

                if (isMineArount(nr, nc)) continue;

                queue.offer(new int[] {nr, nc});
            }
        }
    }

    private static boolean isMineArount(int r, int c) {
        for (int[] dir : DIRS) {
            int nr = r + dir[0], nc = c + dir[1];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            if (board[nr][nc] == MINE) return true;
        }

        return false;
    }
}

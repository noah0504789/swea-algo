package edu.ac.practice.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author noah kim
 * @date 2024/02/08
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV5LtJYKDzsDFAXc&probBoxId=AY13IwlKMEcDFAWX&type=PROBLEM&problemBoxTitle=0205%EC%A3%BC&problemBoxCnt=6
 * @keyword_solution
    Requirements: 가장 많은 방을 이동할 수 있는 초기 방 번호와 이동한 방의 수를 출력하라

    [보드]
    - Size: N*N(1<=N<=1_000)
    [방]
    - Unique (1<=A<=N^2)
    - Mobile
      1. Cardinally
      2. Successively
 * @input TC 별
    - N
    - Board
 * @output
    - 처음에 출발해야 하는 방 번호(중복일 경우, 작은 방으로), 최대로 이동한 방 수
 * @time_complex
 * @perf
 */
public class 정사각형_방 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final int[][] DIRS = {{0,1}, {0,-1}, {-1,0}, {1,0}};
    private static int[][] board, cache;
    private static int T, N, initRoom, maxRoomCnt;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            board = new int[N][N];
            cache = new int[N][N];

            for (int r = 0; r < N; r++) board[r] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    dfs(r, c);

                    if (cache[r][c] > maxRoomCnt) {
                        maxRoomCnt = cache[r][c];
                        initRoom = board[r][c];
                    } else if (cache[r][c] == maxRoomCnt) {
                        initRoom = Math.min(initRoom, board[r][c]);
                    }
                }
            }

            sb.append("#"+t+" "+initRoom+" "+(maxRoomCnt+1)).append("\n");

            initRoom = maxRoomCnt = 0;
        }

        System.out.println(sb);
    }

    private static int dfs(int r, int c) {
        if (cache[r][c] > 0) return cache[r][c];

        for (int[] dir : DIRS) {
            int nr = r + dir[0], nc = c + dir[1];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            if (board[nr][nc] != board[r][c]+1) continue;

            cache[r][c] += dfs(nr, nc) + 1;

            break;
        }

        return cache[r][c];
    }
}

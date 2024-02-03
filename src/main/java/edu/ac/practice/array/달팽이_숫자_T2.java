package edu.ac.practice.array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author noah kim
 * @date 2024-01-31
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV5PobmqAPoDFAUq&probBoxId=AY0LFFoqrIMDFAXz+&type=PROBLEM&problemBoxTitle=0129%EC%A3%BC&problemBoxCnt=++5+
 * @keyword_solution
    [달팽이]
    - 보드 : N*N
    - 탐색 : 1부터 시계방향으로
    - 칸 : 자연수 (~N)
 * @input
    - 테스트케이스 갯수
    - while (테스트케이스 갯수) N
 * @output
    - 테스트케이스 별, 달팽이 숫자
 * @time_complex O(N^2)
 * @perf 18,256 kb / 103 ms
 */
public class 달팽이_숫자_T2 {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static final int RIGHT_IDX = 0, DOWN_IDX = 1, LEFT_IDX = 2, UP_IDX = 3;
    static StringBuffer sb = new StringBuffer();
    static int[][] board;
    static int T, N, r, c, nr, nc, CUR_IDX;
    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            CUR_IDX = RIGHT_IDX;

            r = 0;
            c = 0;

            for (int num = 1; num <= N * N; num++) {
                board[r][c] = num;

                nr = r + DIRS[CUR_IDX][0];
                nc = c + DIRS[CUR_IDX][1];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N || board[nr][nc] != 0) {
                    CUR_IDX = (CUR_IDX+1) % DIRS.length;
                    nr = r + DIRS[CUR_IDX][0];
                    nc = c + DIRS[CUR_IDX][1];
                }

                r = nr;
                c = nc;
            }

            sb.append("#" + t);
            sb.append("\n");

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    sb.append(board[i][j] + " ");
                }
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());

        br.close();
    }
}

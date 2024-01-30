package edu.ac.practice.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author noah kim
 * @date 2024-01-30
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV14ABYKADACFAYh&probBoxId=AY0LFFoqrIMDFAXz+&type=PROBLEM&problemBoxTitle=0129%EC%A3%BC&problemBoxCnt=4
 * @keyword_solution
    사다리 게임
    - 어느 사다리를 고르면 X표시 좌표에 도착하는지 컬럼값 출력

    * 사다리 배열
    - 크기 : 100 * 100
    - 평면좌표 : 0
    - 사다리좌표 : 1
    - 도착지점좌표 : 2

     * 입력
    - 테스트케이스

     * 출력
    - 출발점의 컬럼 좌표
 * @input
 * @output
 * @time_complex
 * @perf 41,272 kb / 261 ms
 */

public class Ladder {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int T = 10, ROW = 100, COL = 100, UP_IDX = 0, LEFT_IDX = 1, RIGHT_IDX = 2, lastCol, ans, nr, nc;
    private static int[][] board, DIRS = {{-1, 0}, {0, -1}, {0, 1}}; // UP, LEFT, RIGHT


    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            br.readLine();

            makeBoard();

            for (int c = 0; c < COL; c++) {
                if (board[ROW-1][c] != 2) continue;

                lastCol = c;
                break;
            }

            getStartColIdx(ROW-1, lastCol, 2);

            System.out.printf("#%d %d\n", t, ans);
        }

        br.close();
    }

    private static void makeBoard() throws IOException {
        board = new int[ROW][];

        for (int r = 0; r < ROW; r++) {
            board[r] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
    }

    private static boolean getStartColIdx(int r, int c, int dirIdx) {
        if (r == 0 && board[r][c] == 1) {
            ans = c;
            return true;
        }

        if (r < 0 || r > ROW-1 || c < 0 || c > COL-1) return false;
        if (board[r][c] == 0) return false;

        if (dirIdx == UP_IDX) {
            if (isAvailableToVisit(r, c, LEFT_IDX)) return getStartColIdx(nr, nc, LEFT_IDX);
            if (isAvailableToVisit(r, c, RIGHT_IDX)) return getStartColIdx(nr, nc, RIGHT_IDX);

            nr = r + DIRS[dirIdx][0];
            nc = c + DIRS[dirIdx][1];

            return getStartColIdx(nr, nc, dirIdx);
        }

        if (isAvailableToVisit(r, c, UP_IDX)) return getStartColIdx(nr, nc, UP_IDX);

        nr = r + DIRS[dirIdx][0];
        nc = c + DIRS[dirIdx][1];

        return getStartColIdx(nr, nc, dirIdx);
    }

    private static boolean isAvailableToVisit(int r, int c, int dirIdx) {
        nr = r + DIRS[dirIdx][0];
        nc = c + DIRS[dirIdx][1];

        return nc >= 0 && nc < COL && board[nr][nc] == 1;
    }
}

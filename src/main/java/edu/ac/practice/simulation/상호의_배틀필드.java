package edu.ac.practice.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024-02-15
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV5LyE7KD2ADFAXc&probBoxId=AY2gBgM6OSIDFAXh&type=PROBLEM&problemBoxTitle=0212%EC%A3%BC&problemBoxCnt=5
 * @requirement 전차를 조작하고 난 후, 맵의 상태를 출력하라.
 * @keyword
    [맵]
      - 요소: 평지(.)-이동 가능, 벽돌(*), 강철(#), 물(-)
    [전차]
      - 갯수: 1대
      - 상태: 상(^), 하(v), 좌(<), 우(>)
      - 동작: 직사포격(벽돌 명중 -> 평지로 전환)
    [명령어: 회전]
      - 종류: U(Up), D(Down), L(Left), R(Right)
        - 이동 조건: 회전한 방향의 다음 칸이 평지일 경우
    [명령어: 포격]
      - S(Shoot): (현재 상태의 방향으로) 직사포격
 * @input TC
    - H: 높이 (2<=H<=20), W: 너비 (2<=W<=20)
    - N: 명령어 개수 (0<=N<=100)
    - 전체 명령어
 * @time_complex
 * @perf 19,888 kb / 130 ms
 */
public class 상호의_배틀필드 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final char GROUND = '.';
    private static final char BRICK_WALL = '*';
    private static final char STEEL_WALL = '#';
    private static final char WATER = '-';
    private static final char UP_STATE = '^';
    private static final char DOWN_STATE = 'v';
    private static final char LEFT_STATE = '<';
    private static final char RIGHT_STATE = '>';
    private static final char UP_CMD = 'U';
    private static final char DOWN_CMD = 'D';
    private static final char LEFT_CMD = 'L';
    private static final char RIGHT_CMD = 'R';
    private static final char SHOOT_CMD = 'S';
    private static StringTokenizer st;
    private static String line, allCmd;
    private static char[][] map;
    private static int TC, H, W, N, cr, cc, nr, nc, bwr, bwc;

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            cr = 0;
            cc = 0;
            nr = 0;
            nc = 0;
            bwr = 0;
            bwc = 0;

            map = new char[H][W];
            for (int r = 0; r < H; r++) {
                line = br.readLine();
                for (int c = 0; c < W; c++) {
                    char state = line.charAt(c);
                    map[r][c] = state;

                    if (isTank(state)) {
                        cr = r;
                        cc = c;
                    }
                }
            }

            N = Integer.parseInt(br.readLine());
            allCmd = br.readLine();

            for (char cmd : allCmd.toCharArray()) {
                if (isStateChgCmd(cmd)) {
                    chgState(cmd);
                    if (isMoveable()) move();
                } else if(isShootCmd(cmd)) {
                    if (isDestructible()) destroy();
                }
            }

            sb.append("#"+t+" ");

            for (int r = 0; r < H; r++) {
                for (int c = 0; c < W; c++) {
                    sb.append(map[r][c]);
                }
                sb.append("\n");
            }
        }

        System.out.println(sb);

        br.close();
    }

    private static boolean isTank(char state) {
        return state == UP_STATE || state == DOWN_STATE || state == LEFT_STATE || state == RIGHT_STATE;
    }

    private static boolean isStateChgCmd(char cmd) {
        return cmd == UP_CMD || cmd == DOWN_CMD || cmd == LEFT_CMD || cmd == RIGHT_CMD;
    }

    private static void chgState(char cmd) {
        if (cmd == UP_CMD) map[cr][cc] = UP_STATE;
        else if (cmd == DOWN_CMD) map[cr][cc] = DOWN_STATE;
        else if (cmd == LEFT_CMD) map[cr][cc] = LEFT_STATE;
        else if (cmd == RIGHT_CMD) map[cr][cc] = RIGHT_STATE;
    }

    private static boolean isMoveable() {
        char state = map[cr][cc];
        nr = cr;
        nc = cc;

        if (state == UP_STATE) nr -= 1;
        else if (state == DOWN_STATE) nr += 1;
        else if (state == LEFT_STATE) nc -= 1;
        else if (state == RIGHT_STATE) nc += 1;

        if (nr < 0 || nr >= H || nc < 0 || nc >= W) return false;

        return map[nr][nc] == GROUND;
    }

    private static void move() {
        char tmp = map[cr][cc];
        map[cr][cc] = map[nr][nc];
        map[nr][nc] = tmp;

        cr = nr;
        cc = nc;
    }

    private static boolean isShootCmd(char cmd) {
        return cmd == SHOOT_CMD;
    }

    private static boolean isDestructible() {
        char state = map[cr][cc];

        if (state == UP_STATE) return isBrickBelowSteel();
        else if (state == DOWN_STATE) return isBrickAboveSteel();
        else if (state == LEFT_STATE) return isBrickRightSteel();

        return isBrickLeftSteel();
    }

    private static boolean isBrickBelowSteel() {
        for (int r = cr-1; r >= 0; r--) {
            char curState = map[r][cc];

            if (curState == STEEL_WALL) {
                return false;
            } else if (curState == BRICK_WALL) {
                bwr = r;
                bwc = cc;
                return true;
            }
        }

        return false;
    }

    private static boolean isBrickAboveSteel() {
        for (int r = cr+1; r < H; r++) {
            char curState = map[r][cc];

            if (curState == STEEL_WALL) {
                return false;
            } else if (curState == BRICK_WALL) {
                bwr = r;
                bwc = cc;
                return true;
            }
        }

        return false;
    }

    private static boolean isBrickRightSteel() {
        for (int c = cc-1; c >= 0; c--) {
            char curState = map[cr][c];

            if (curState == STEEL_WALL) {
                return false;
            } else if (curState == BRICK_WALL) {
                bwr = cr;
                bwc = c;
                return true;
            }
        }

        return false;
    }

    private static boolean isBrickLeftSteel() {
        for (int c = cc+1; c < W; c++) {
            char curState = map[cr][c];

            if (curState == STEEL_WALL) {
                return false;
            } else if (curState == BRICK_WALL) {
                bwr = cr;
                bwc = c;
                return true;
            }
        }

        return false;
    }

    private static void destroy() {
        map[bwr][bwc] = GROUND;
    }
}

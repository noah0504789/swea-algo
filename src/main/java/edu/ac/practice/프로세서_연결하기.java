package edu.ac.practice;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-02-28
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV4suNtaXFEDFAUf&probBoxId=AY3s7Te6B9kDFAUZ&type=PROBLEM&problemBoxTitle=0226%EC%A3%BC&problemBoxCnt=1
 * @requirement 최대한 많은 core에 전원을 연결하였을 때, 전선 길이의 최소합을 구하라
 * @keyword
    [보드]
    - 크기: N*N
    - 칸: blank(0), core(1)

    [전원연결]
    - 가장자리: 자동전원공급
    - 케이블 설치
      - 방향: 직선
      - 조건: 케이블 교차 불가
 * @input TC:input
    - N:보드 폭 (7<=N<=12)
    - M:코어 갯수 (1<=M<=12)
    - 보드 정보

1
7
0 0 1 0 0 0 0
0 0 1 0 0 0 0
0 0 0 0 0 1 0
0 0 0 0 0 0 0
1 1 0 1 0 0 0
0 1 0 0 0 0 0
0 0 0 0 0 0 0

 * @output
 * @time_complex
 * @perf
 */
public class 프로세서_연결하기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final List<List<Core>> cores = new ArrayList<>();
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int CORE = 1;
    private static StringTokenizer st;
    private static int[][] board;
    private static int iRowPresenceBit, iColPresenceBit;
    private static int TC, N, layerCnt, ans;
    static class Core {
        final int r, c;
        int dirBit;

        public Core(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public boolean hasDir(int dir) {
            return (dirBit&(1<<dir)) > 0;
        }

        public void dirOn() {
            if (canPutUp()) dirBit |= (1<<UP);
            if (canPutRight()) dirBit |= (1<<RIGHT);
            if (canPutDown()) dirBit |= (1<<DOWN);
            if (canPutLeft()) dirBit |= (1<<LEFT);
        }

        private boolean canPutUp(int rBit) {
            int pr = r;

            while (pr-- >= 0) {
                if ((rBit &(1<<pr))>0) return false;
            }

            return true;
        }

        private boolean canPutDown(int rBit) {
            int nr = r;

            while (nr++ < N) {
                if ((rBit &(1<<nr))>0) return false;
            }

            return true;
        }

        private boolean canPutLeft(int cBit) {
            int pc = c;

            while (pc-- >= 0) {
                if ((cBit &(1<<pc))>0) return false;
            }

            return true;
        }

        private boolean canPutRight(int cBit) {
            int nc = c;

            while (nc++ < N) {
                if ((cBit &(1<<nc))>0) return false;
            }

            return true;
        }

        private boolean canPutUp() {
            return canPutUp(iRowPresenceBit);
        }

        private boolean canPutDown() {
            return canPutDown(iRowPresenceBit);
        }

        private boolean canPutLeft() {
            return canPutLeft(iColPresenceBit);
        }

        private boolean canPutRight() {
            return canPutRight(iColPresenceBit);
        }

        public int markRowBit(int dir, int curRowBit) {
            return dir == UP ? markRowUp(curRowBit) : markRowDown(curRowBit);
        }

        public int markColBit(int dir, int curColBit) {
            return dir == LEFT ? markRowLeft(curColBit) : markRowRight(curColBit);
        }

        private int markRowUp(int curRowBit) {
            int pr = r, markedRowBit = curRowBit;

            while (pr-- >= 0) markedRowBit |= (1<<pr);

            return markedRowBit;
        }

        private int markRowDown(int curRowBit) {
            int nr = r, markedRowBit = curRowBit;

            while (nr++ < N) markedRowBit |= (1<<nr);

            return markedRowBit;
        }

        private int markRowLeft(int curColBit) {
            int pc = c, markedColBit = curColBit;

            while (pc-- >= 0) markedColBit |= (1<<pc);

            return markedColBit;
        }

        private int markRowRight(int curColBit) {
            int nc = c, markedColBit = curColBit;

            while (nc++ < N) markedColBit |= (1<<nc);

            return markedColBit;
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            ans = Integer.MAX_VALUE;

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    board[r][c] = Integer.parseInt(st.nextToken());

                    if (board[r][c] == CORE) {
                        iRowPresenceBit |= (1<<r);
                        iColPresenceBit |= (1<<c);
                    }
                }
            }

            layerCnt = N%2==0 ? N/2 : N/2+1;

            for (int i = 0; i < layerCnt; i++) cores.add(new ArrayList<>());

            // TODO: layer별 core에 놓을 수 있는 간선 방향 저장 (순서: 왼쪽 상단 -> 오른쪽 하단)
            saveCore(0);

            // TODO: (재귀) layer별 조합+백트래킹
            sumCableLength(0, 0, 0, 0, 0);

            sb.append("#"+t+" ").append(ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void saveCore(int layer) {
        if (layer == layerCnt) return;

        List<Core> coreList = cores.get(layer);

        int first = layer, last = N-layer;

        for (int r = first; r < last; r++) {
            for (int c = first; c < last; c++) {
                if ((r>first && r<last-1) && (c>first && c<last-1)) continue;

                Core core = new Core(r, c);

                if (layer > 0) core.dirOn();

                coreList.add(core);
            }
        }

        saveCore(layer+1);
    }

    private static void sumCableLength(int layer, int idx, int sum, int rpBit, int cpBit) {
        if (layer == layerCnt) {
            ans = Math.min(ans, sum);
            return;
        }

        if (idx == cores.get(layer).size()) {
            sumCableLength(layer+1, 0, sum, rpBit, cpBit);
            return;
        }

        Core core = cores.get(layer).get(idx);

        for (int dir = 0; dir < 4; dir++) {
            if (!core.hasDir(dir)) continue;

            int cableLength = 0;

            if (dir == UP || dir == DOWN) {
                cableLength = Integer.bitCount(rpBit);
                rpBit = core.markRowBit(dir, rpBit);
                cableLength = Integer.bitCount(rpBit)-cableLength;
            } else if (dir == RIGHT || dir == LEFT) {
                cableLength = Integer.bitCount(cpBit);
                cpBit = core.markColBit(dir, cpBit);
                cableLength = Integer.bitCount(cpBit)-cableLength;
            }

            sumCableLength(layer, idx+1, sum+cableLength, rpBit, cpBit);
        }
    }
}

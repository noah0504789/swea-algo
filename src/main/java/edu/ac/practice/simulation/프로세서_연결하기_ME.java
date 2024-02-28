package edu.ac.practice.simulation;

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
public class 프로세서_연결하기_ME {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuilder sb = new StringBuilder();
    private static final List<List<Core>> cores = new ArrayList<>();
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;
    private static final int LEFT = 4;
    private static final int BLANK = 0;
    private static final int CORE = 1;
    private static StringTokenizer st;
    private static int[][] board;
    private static BitSet corePresentBit;
    private static int TC, N, layerCnt, maxCnt, ans;
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

        public void setDir() {
            if (canPutUp()) dirBit |= (1<<UP);
            if (canPutRight()) dirBit |= (1<<RIGHT);
            if (canPutDown()) dirBit |= (1<<DOWN);
            if (canPutLeft()) dirBit |= (1<<LEFT);
        }

        private boolean canPutUp(BitSet curState) {
            int pr = r-1;

            while (pr >= 0) {
                if ((curState.get(pr--*N+c))) return false;
            }

            return true;
        }

        private boolean canPutUp() {
            return canPutUp(corePresentBit);
        }

        private boolean canPutDown(BitSet curState) {
            int nr = r+1;

            while (nr < N) {
                if ((curState.get(nr++*N+c))) return false;
            }

            return true;
        }

        private boolean canPutDown() {
            return canPutDown(corePresentBit);
        }

        private boolean canPutLeft(BitSet curState) {
            int pc = c-1;

            while (pc >= 0) {
                if ((curState.get(r*N+pc--))) return false;
            }

            return true;
        }

        private boolean canPutLeft() {
            return canPutLeft(corePresentBit);
        }

        private boolean canPutRight(BitSet curState) {
            int nc = c+1;

            while (nc < N) {
                if ((curState.get(r*N+nc++))) return false;
            }

            return true;
        }

        private boolean canPutRight() {
            return canPutRight(corePresentBit);
        }

        private void markRowUp(BitSet curState) {
            int pr = r-1;

            while (pr >= 0) {
                int cutBit = pr-- * N + c;
                if (curState.get(cutBit)) {
                    curState = null;
                    return;
                }
                curState.set(cutBit);
            }
        }

        private void markRowDown(BitSet curState) {
            int nr = r+1;

            while (nr < N) {
                int cutBit = nr++ * N + c;
                if (curState.get(cutBit)) {
                    curState = null;
                    return;
                }
                curState.set(cutBit);
            }
        }

        private void markRowLeft(BitSet curState) {
            int pc = c-1;

            while (pc >= 0) {
                int cutBit = r * N + pc--;
                if (curState.get(cutBit)) {
                    curState = null;
                    return;
                }
                curState.set(cutBit);
            }
        }

        private void markRowRight(BitSet curState) {
            int nc = c+1;

            while (nc < N) {
                int cutBit = r * N + nc++;
                if (curState.get(cutBit)) {
                    curState = null;
                    return;
                }
                curState.set(cutBit);
            }
        }

        public BitSet putCable(int dir, BitSet curState) {
            if (dir == UP) { markRowUp(curState); }
            else if (dir == RIGHT) { markRowDown(curState); }
            else if (dir == DOWN) { markRowRight(curState); }
            else if (dir == LEFT) { markRowLeft(curState); }

            return curState;
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            maxCnt = 0;
            ans = Integer.MAX_VALUE;
            corePresentBit = new BitSet(N*N);

            for (int r = 0; r < N; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < N; c++) {
                    board[r][c] = Integer.parseInt(st.nextToken());

                    if (board[r][c] == CORE) corePresentBit.set(r*N+c);
                }
            }

            layerCnt = N%2==0 ? N/2 : N/2+1;

            for (int i = 0; i < layerCnt; i++) cores.add(new ArrayList<>());

            saveCore(0);

            sumCableLength(1, 0, 0, 0, new BitSet());

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
                if (board[r][c] == BLANK) continue;

                Core core = new Core(r, c);
                if (layer > 0) core.setDir();

                coreList.add(core);
            }
        }

        saveCore(layer+1);
    }

    private static void sumCableLength(int layer, int idx, int cnt, int sum, BitSet curState) {
        if (layer == layerCnt) {
            if (cnt > maxCnt) {
                maxCnt = cnt;
                ans = sum;
            } else if (cnt == maxCnt) {
                ans = Math.min(ans, sum);
            }
            return;
        }

        if (idx == cores.get(layer).size()) {
            sumCableLength(layer+1, 0, sum, cnt, curState);
            return;
        }

        Core core = cores.get(layer).get(idx);

        for (int dir = 1; dir <= 4; dir++) {
            if (!core.hasDir(dir)) continue;

            BitSet cabledState = core.putCable(dir, (BitSet) curState.clone());
            if (cabledState == null) {
                sumCableLength(layer, idx+1, sum, cnt, (BitSet) curState.clone());
                continue;
            }

            int cableLength = cabledState.cardinality() - curState.cardinality();

            sumCableLength(layer, idx+1, sum+cableLength, cnt+1, cabledState);
        }
    }
}

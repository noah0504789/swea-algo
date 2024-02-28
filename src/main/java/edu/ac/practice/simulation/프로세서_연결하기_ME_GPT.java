package edu.ac.practice.simulation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

public class 프로세서_연결하기_ME_GPT {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringBuilder sb = new StringBuilder();
    private static List<Core> cores = new ArrayList<>();
    private static int N, maxCnt, minLen;
    private static int[][] board;
    private static final int[] dx = {0, 0, -1, 1}; // UP, DOWN, LEFT, RIGHT
    private static final int[] dy = {-1, 1, 0, 0};

    static class Core {
        int r, c;

        Core(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {
        int TC = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= TC; t++) {
            N = Integer.parseInt(br.readLine().trim());
            board = new int[N][N];
            cores.clear();
            maxCnt = 0;
            minLen = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] == 1 && i != 0 && j != 0 && i != N - 1 && j != N - 1) {
                        cores.add(new Core(i, j));
                    }
                }
            }

            dfs(0, 0, 0, new BitSet(N * N));
            sb.append("#").append(t).append(" ").append(minLen).append("\n");
        }
        System.out.println(sb.toString());
        br.close();
    }

    private static void dfs(int index, int cnt, int len, BitSet state) {
        if (index == cores.size()) {
            if (cnt > maxCnt || (cnt == maxCnt && len < minLen)) {
                maxCnt = cnt;
                minLen = len;
            }
            return;
        }

        Core core = cores.get(index);
        boolean extended = false;
        for (int i = 0; i < 4; i++) {
            BitSet newState = (BitSet) state.clone();
            int addedLength = putCableAndGetLength(newState, core, i);
            if (addedLength > 0) {
                extended = true;
                dfs(index + 1, cnt + 1, len + addedLength, newState);
            }
        }

        // If no extension was possible, try the next core without connecting this one.
        if (!extended) {
            dfs(index + 1, cnt, len, state);
        }
    }

    private static int putCableAndGetLength(BitSet state, Core core, int dir) {
        int length = 0, x = core.r, y = core.c;

        while (true) {
            x += dx[dir];
            y += dy[dir];

            if (x < 0 || y < 0 || x >= N || y >= N) break; // Reached the boundary or an obstacle
            if (state.get(x * N + y)) return 0; // Found an obstacle

            state.set(x * N + y);
            length++;
        }

        return length;
    }
}

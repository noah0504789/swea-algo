package org.swea.array;

import java.io.*;
import java.util.*;

class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, ans, SIZE, N = 1, S = 2;
    static int[][] board;

    public static void main(String args[]) throws Exception {
        T = 10;

        for(int t = 1; t <= T; t++) {
            init();

            for (int i = 0; i < SIZE; i++) {
                int prevRowIdx = 0, rowIdx = 0, prevM = 0, m = 0;
                while (prevRowIdx < SIZE) {
                    while (prevRowIdx < SIZE) {
                        prevM = board[prevRowIdx++][i];
                        if (prevM == N) break;
                    }

                    rowIdx = prevRowIdx;
                    while (rowIdx < SIZE) {
                        m = board[rowIdx++][i];
                        if (m == S) {
                            ans++;
                            break;
                        }
                    }

                    prevRowIdx = rowIdx;
                }
            }

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static void init() throws IOException {
        // 입력
        SIZE = Integer.parseInt(br.readLine());
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ans = 0;
    }
}

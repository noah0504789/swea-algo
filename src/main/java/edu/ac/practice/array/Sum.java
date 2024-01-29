package edu.ac.practice.array;

import java.io.*;
import java.util.*;

class Sum {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, tc, SIZE = 100;
    static long ans;
    static int[][] board;

    public static void main(String args[]) throws Exception {
        T = 10;

        for(int t = 1; t <= T; t++) {
            init();

            int rDiagonal = 0, lDiagonal = 0;
            for (int i = 0; i < SIZE; i++) {
                rDiagonal +=board[i][i];
                lDiagonal +=board[i][SIZE-1-i];
                int rSum = getRowSum(i);
                int cSum = getColSum(i);

                ans = Math.max(ans, Math.max(rSum, cSum));
            }

            ans = Math.max(ans, Math.max(rDiagonal, lDiagonal));

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static int getRowSum(int rIdx) {
        return Arrays.stream(board[rIdx]).sum();
    }

    private static int getColSum(int cIdx) {
        int sum = 0;
        for (int i = 0; i < SIZE; i++) sum += board[i][cIdx];

        return sum;
    }

    private static void init() throws IOException {
        // 입력
        tc = Integer.parseInt(br.readLine());
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ans=0;
    }
}

package edu.ac.practice.array;

import java.io.*;
import java.util.*;

public class 회문1 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, length, ans, SIZE = 8, DIR_LENGTH = 4, UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    static int[] input, opposite;
    static char[][] board;
    static boolean[][][] visited;

    public static void main(String args[]) throws Exception {
        T = 10;

        for(int t = 1; t <= T; t++) {
            init();

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    checkPalindrome(i, j);
                }
            }

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static void checkPalindrome(int y, int x) {
        for (int i = 0; i <DIR_LENGTH; i++) {
            if (isOutOfRange(y, x, i)) continue;
            if (isCounted(y, x, i)) continue;
            if (isPalindrome(y, x, i)) {
                ans++;
                visited[i][y][x] = true;
            }
        }
    }

    private static boolean isPalindrome(int y, int x, int d) {
        int l, r;
        if (d == UP) {
            l = y-length+1;
            r = y;
            while (l <= r) {
                if (board[l++][x] != board[r--][x]) return false;
            }
        } else if (d == DOWN) {
            l = y;
            r = y+length-1;
            while (l <= r) {
                if (board[l++][x] != board[r--][x]) return false;
            }
        } else if (d == LEFT) {
            l = x-length+1;
            r = x;
            while (l <= r) {
                if (board[y][l++] != board[y][r--]) return false;
            }
        } else if (d == RIGHT) {
            l = x;
            r = x+length-1;
            while (l <= r) {
                if (board[y][l++] != board[y][r--]) return false;
            }
        }

        return true;
    }

    private static boolean isCounted(int y, int x, int d) {
        int od = opposite[d];
        if (d == UP && visited[od][y-length+1][x]) {
            return true;
        } else if (d == DOWN && visited[od][y+length-1][x]) {
            return true;
        } else if (d == LEFT && visited[od][y][x-length+1]) {
            return true;
        } else if (d == RIGHT && visited[od][y][x+length-1]) {
            return true;
        }

        return false;
    }

    private static boolean isOutOfRange(int y, int x, int d) {
        if (d == UP && y-length+1 < 0) {
            return true;
        } else if (d == DOWN && y+length-1> SIZE-1) {
            return true;
        } else if (d == LEFT && x-length+1< 0) {
            return true;
        } else if (d == RIGHT && x+length-1> SIZE-1) {
            return true;
        }

        return false;
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        length = input[0];
        board = new char[SIZE][SIZE];
        opposite = new int[DIR_LENGTH];
        opposite[UP] = DOWN;
        opposite[DOWN] = UP;
        opposite[LEFT] = RIGHT;
        opposite[RIGHT] = LEFT;

        for (int i = 0; i < SIZE; i++) board[i] = br.readLine().toCharArray();
        visited = new boolean[DIR_LENGTH][SIZE][SIZE];

        ans=0;
    }
}


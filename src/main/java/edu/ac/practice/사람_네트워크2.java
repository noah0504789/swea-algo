package edu.ac.practice;

import java.io.*;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-03-28
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV18P2B6Iu8CFAZN
 * @requirement
 * @keyword
 * @input
 * @output
 * @time_complex
 * @perf
 */
public class 사람_네트워크2 {
    private static StringBuilder sb = new StringBuilder();
    private static int[][] table;
    private static int TC, n;

    public static void main(String[] args) throws IOException {
        TC = readInt();

        for (int t = 1; t <= TC; t++) {
            n = readInt();
            table = new int[n][n];

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    table[r][c] = readInt();
                }
            }

            floydWarshall();

            sb.append("#").append(t).append(" ").append(shortest());
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static int shortest() {
        int result = Integer.MAX_VALUE;
        for (int r = 0; r < n; r++) {
            result = Math.min(result, Arrays.stream(table[r]).sum());
        }

        return result;
    }

    private static void floydWarshall() {
        for (int stop = 0; stop < n; stop++) {
            for (int src = 0; src < n; src++) {
                for (int dest = 0; dest < n; dest++) {
                    if (src == dest) continue;
                    table[src][dest] = Math.min(table[src][dest], table[src][stop] + table[stop][dest]);
                }
            }
        }
    }

    private static int readInt() throws IOException {
        int c, n = System.in.read() & (1<<4)-1;
        while ((c = System.in.read()) > (1<<5)) {
            n = (n<<1) + (n<<3) + (c & (1<<4)-1);
        }
        return n;
    }
}

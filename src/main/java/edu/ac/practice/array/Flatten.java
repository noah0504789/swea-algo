package edu.ac.practice.array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024-01-31
 * @link
 * @keyword_solution
 * @input
 * @output
 * @time_complex
 * @perf
 */
public class Flatten {
    static int[] height = new int[101];
    static int T = 10;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int t = 1; t <= T; t++) {
            for (int i = 0; i < 101; i++) height[i] = 0;
            int dumps = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int min = 100, max = 0;
            for (int n = 0; n < 100; n++) {
                int h = Integer.parseInt(st.nextToken());
                height[h]++;
                min = Math.min(min, h);
                max = Math.max(max, h);
            }
            int ans = solve(min, max, dumps);
            System.out.printf("#%d %d\n", t, ans);
        }
    }

    private static int solve(int min, int max, int dumps) {
        while (dumps-- > 0) {
            if (max - min < 2) break;

            height[min+1]++;
            height[max-1]++;
            if (--height[min] == 0) min++;
            if (--height[max] == 0) max--;
        }

        return max-min;
    }
}

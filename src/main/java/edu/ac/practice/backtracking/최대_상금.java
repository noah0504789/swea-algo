package edu.ac.practice.backtracking;
import java.io.*;
import java.util.*;

public class 최대_상금 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, num, change, length, ans;
    static int[] input, nums;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();
            ans = 0;
            change = Math.min(change, length);

            dfs(0, 0);

            System.out.println("#" + t + " " + ans);
        }
    }

    private static void dfs(int start, int depth) {
        if (depth == change) {
            StringBuffer sb = new StringBuffer();
            for (int v : nums) sb.append(v);
            int val = Integer.parseInt(sb.toString());
            ans = Math.max(ans, val);
            return;
        }

        for (int i = start; i < length; i++) {
            for (int j = i+1; j < length; j++) {
                swap(i, j);
                dfs(i, depth+1);
                swap(j, i);
            }
        }
    }

    private static void swap(int srcIdx, int destIdx) {
        int tmp = nums[srcIdx];
        nums[srcIdx] = nums[destIdx];
        nums[destIdx] = tmp;
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        num = input[0];
        String[] sLst = String.valueOf(num).split("");
        length = sLst.length;
        nums = Arrays.stream(sLst).mapToInt(Integer::parseInt).toArray();

        change = input[1];
    }
}

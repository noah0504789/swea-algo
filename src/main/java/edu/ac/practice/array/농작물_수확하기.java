package edu.ac.practice.array;

import java.io.*;
import java.util.*;

class 농작물_수확하기 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N, ans;
    static int[] input;
    static int[][] nums;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            int start = N/2;
            ans = Arrays.stream(nums[start]).sum();
            int delta = 1;

            while (start-delta >= 0) {
                int startUp = start-delta;
                int startDown = start+delta;

                ans+=nums[startUp][start];
                ans+=nums[startDown][start];
                for (int i = 1; i <= startUp; i++) {
                    ans+=nums[startUp][start-i];
                    ans+=nums[startUp][start+i];
                    ans+=nums[startDown][start-i];
                    ans+=nums[startDown][start+i];
                }

                delta++;
            }

            System.out.println("#" + t + " " + ans);
        }
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = input[0];
        nums = new int[N][N];
        for (int i = 0; i < N; i++) nums[i] = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
        ans = 0;
    }
}

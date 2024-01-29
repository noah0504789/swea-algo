package edu.ac.practice.array;

import java.io.*;
import java.util.*;

class 백만장자_프로젝트 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, N;
    static long ans;
    static int[] nums;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            int max = 0;
            for (int i = N-1; i >=0; i--) {
                if (nums[i] >= max) max = nums[i];
                else ans += (max - nums[i]);
            }

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static void init() throws IOException {
        // 입력
        N = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ans = 0;
    }
}

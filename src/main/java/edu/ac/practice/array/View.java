package edu.ac.practice.array;

import java.util.*;
import java.io.*;

public class View {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        int T=10, N, ans;
        int[] nums;

        for(int t = 1; t <= T; t++) {
            N = sc.nextInt();
            nums = new int[N];

            for (int i = 0; i < N; i++) nums[i] = sc.nextInt();

            ans = 0;
            for (int i = 2; i < N-2; i++) {
                int height = nums[i];
                int max = Math.max(nums[i-2], Math.max(nums[i-1], Math.max(nums[i+1], nums[i+2])));
                if (height > max) {
                    ans += height-max;
                }
            }

            System.out.println("#" + t + " " + ans);
        }
    }
}

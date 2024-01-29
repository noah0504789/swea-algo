package edu.ac.practice.dfs;

import java.io.*;
import java.util.*;

// dp
// 출처 : https://j3sung.tistory.com/802
class 햄버거_다이어트 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, cnt, limit;
    static int[] input, taste, calory;
    static int[][] dp; // j 무게까지 한도를 두어 i 까지의 재료를 사용하였을 때, 만들 수 있는 최대 가치

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            for (int i = 1; i <= cnt; i++) {
                for (int j = 1; j <= limit; j++) {
                    if (calory[i] > j) dp[i][j] = dp[i-1][j];
                    else dp[i][j] = Math.max(dp[i-1][j], taste[i]+dp[i-1][j-calory[i]]);
                }
            }

            System.out.println("#"+ t + " " + dp[calory.length][limit]);
        }
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        cnt = input[0];
        limit = input[1];
        taste = new int[cnt];
        calory = new int[cnt];

        for (int i = 0; i < cnt; i++) {
            input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            taste[i] = input[0];
            calory[i] = input[1];
        }

        dp = new int[cnt+1][limit+1];
    }
}

// dfs
// 출처 : https://javacoding.tistory.com/36
class 햄버거_다이어트_2 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, ans, cnt, limit;
    static int[] input, taste, calory;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            dfs(0, 0, 0);

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static void dfs(int start, int score, int cal) {
        if (cal > limit) return;
        if (start == cnt) {
            ans = Math.max(ans, score);
            return;
        }

        dfs(start+1, score+taste[start], cal + calory[start]);
        dfs(start+1, score, cal);
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        cnt = input[0];
        limit = input[1];
        taste = new int[cnt];
        calory = new int[cnt];

        for (int i = 0; i < cnt; i++) {
            input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            taste[i] = input[0];
            calory[i] = input[1];
        }

        ans = 0;
    }
}

/**
 import java.io.*;
 import java.util.*;

 class Solution {

 static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 static int T, ans, cnt, limit;
 static int[] input, taste, calory;
 static Map<Integer, Integer> map;

 public static void main(String args[]) throws Exception {
 T = Integer.parseInt(br.readLine());

 for(int t = 1; t <= T; t++) {
 init();

 dfs(0, 0, 0);

 System.out.println("#"+ t + " " + ans);
 }
 }

 private static void dfs(int start, int tasteSum, int calorySum) {
 if (calorySum > limit) {
 ans = Math.max(ans, tasteSum-taste[start-1]);
 return;
 }

 if (calorySum == limit) {
 ans = Math.max(ans, tasteSum);
 return;
 }

 if (start == cnt) {
 ans = Math.max(ans, tasteSum);
 return;
 }

 for (int i = start; i < cnt; i++) {
 dfs(i+1, tasteSum + taste[i], calorySum + calory[i]);
 }
 }

 private static void init() throws IOException {
 // 입력
 input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
 cnt = input[0];
 limit = input[1];
 taste = new int[cnt];
 calory = new int[cnt];
 map = new HashMap<>();

 for (int i = 0; i < cnt; i++) {
 input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
 taste[i] = input[0];
 calory[i] = input[1];
 map.put(calory[i], taste[i]);
 }

 Arrays.sort(calory);
 for (int i = 0; i < cnt; i++) {
 int c = calory[i];
 int t = map.get(c);
 taste[i] = c;
 }

 ans = 0;
 }
 }
 */

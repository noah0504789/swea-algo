package edu.ac.practice.array;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class 파리_퇴치 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int T, N, M;	
	static int[] input;
	static int[][] board, dp;
	static long ans;
	
	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			init();
			
			for (int i = M; i <= N; i++) {
				for (int j = M; j <= N; j++) {
					int area = dp[i][j] - dp[i-M][j] - dp[i][j-M] + dp[i-M][j-M];
					ans = Math.max(ans, area);
				}
			}
			
			System.out.println("#" + t + " " + ans);
		}
	}
	
	private static void init() throws IOException {
		input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		N = input[0];
		M = input[1];
		
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();			
		}		
		
		dp = new int[N+1][N+1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				dp[i][j] = board[i-1][j-1] + dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1]; 
			}
		}
				
		ans = 0;
	}	
}



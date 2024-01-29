package edu.ac.practice.array;

import java.io.*;
import java.util.*;

public class 달팽이_숫자 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, T;
	static int RIGHT=0, DOWN=1, LEFT=2, UP=3;
	static int[][] nums, DIR = {{0,1},{1,0},{0,-1},{-1,0}};
	
	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			init();
			
			int cy = 0, cx = -1, cd = RIGHT;
			int cVal = 0;
			while (true) {
				if (cVal == N*N) break;
				
				int ny = cy + DIR[cd][0], nx = cx + DIR[cd][1], nVal = cVal+1;				
				if (ny < 0 || ny >= N || nx < 0 || nx >= N || nums[ny][nx] > 0) {
					cd = (cd+1) % DIR.length;					
					continue;
				}
				
				nums[ny][nx] = nVal;
				
				cy = ny;
				cx = nx;				
				cVal++;
			}
			
			System.out.println("#" + t);
			for (int r = 0; r < N; r++) {
				Arrays.stream(nums[r]).forEach(n -> System.out.print(n + " "));
				System.out.println();
			}
		}
	}
	
	private static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
		nums = new int[N][N];
	} 	
}

package edu.ac.practice.string;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class 간단한_369게임 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;	
	
	public static void main(String[] args) throws IOException {
		init();

		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= N; i++) {
			String s = String.valueOf(i);
			long cnt = s.chars().filter(c -> c == '3' || c == '6' || c == '9').count();

			if (cnt > 0) sb.append("-".repeat((int) cnt));
			else sb.append(i);

			sb.append(" ");
		}

		System.out.println(sb.toString().trim());
	}
	
	private static void init() throws IOException {
		N = Integer.parseInt(br.readLine());
	}	
}

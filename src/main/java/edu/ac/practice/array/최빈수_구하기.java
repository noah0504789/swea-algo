// 최빈수
// - 가장 여러 번 나타나는 값
// - 여러개일 경우, 가장 큰 수를 출력하기

package edu.ac.practice.array;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class 최빈수_구하기 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int T, STUDENTS_SIZE = 1000;
	static Entry<Integer, Integer> ans;
	static int[] students;
	static Map<Integer, Integer> map;
	
	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			init();
			
			ans = map.entrySet().stream()
					.sorted((e1, e2) -> e2.getValue() == e1.getValue() ? e2.getKey() - e1.getKey() : e2.getValue()- e1.getValue()) 			
					.findFirst().get();
			
			System.out.println("#" + t + " " + ans.getKey());
		}
	}
	
	private static void init() throws IOException {
		Integer.parseInt(br.readLine());
		students = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		map = new HashMap<>(STUDENTS_SIZE);
		
		Arrays.stream(students).forEach(n -> {
			map.put(n, map.getOrDefault(n, 0)+1);
		});		
	}	
}

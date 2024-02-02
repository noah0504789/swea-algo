package edu.ac.practice.bitmasking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/03
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AXRSXf_a9qsDFAXS
 * @keyword_solution
    Requirements : M의 N번째까지 비트가 모두 1로 켜져있는지 여부를 출력하라. 
 * @input
    - TC
    - TC별 N, M (1<=N<=30, 0<=M<=10^8)
 * @output
    - TC별 ON, OFF 여부
 * @time_complex O(N)
 * @perf
 */
public class 이진수_표현 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final String ON = "ON", OFF = "OFF", INPUT_DELIMITER = " ";
    private static StringTokenizer st;
    private static int T;
    
    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());
        
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine(), INPUT_DELIMITER);
            
            sb.append("#" + t + " " + (isOnAll(st.nextToken(), st.nextToken()) ? ON : OFF) + "\n");
        }

        System.out.println(sb);
    }

    private static boolean isOnAll(String N, String M) {
        int mInt = Integer.parseInt(M);
        for (int digit = 0; digit < Integer.parseInt(N); digit++) {
            if ((mInt & (1 << digit)) != (1 << digit)) return false;
        }
        return true;
    }
}

package edu.ac.practice.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/08
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV141176AIwCFAYD&probBoxId=AY13IwlKMEcDFAWX&type=PROBLEM&problemBoxTitle=0205%EC%A3%BC&problemBoxCnt=6
 * @keyword_solution
    Requirements: 주어진 수식트리가 유효한지 확인하라 (1-valid,0-invalid)

    [수식트리]
    - 완전이진트리
    - 형태
                    연산자
      왼쪽 서브트리 결과     오른쪽 서브트리 결과
    [노드]
    - 총 노드 개수: 1<=N<=200
    - 번호: 레벨별로 순서에 따라 부여됨
 * @input TC: 10
    - N
    - N줄의 정점 정보 (알파벳, 왼쪽자식번호, 오른쪽자식번호)
 * @output
    - 수식트리의 유효성 검사
 * @time_complex
 * @perf
 */
public class 사칙연산_유효성_검사 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final int T = 10;
    private static StringTokenizer st;
    private static Set<String> set = new HashSet<>(Arrays.asList("+","-", "*", "/"));
    private static boolean isCompleted;
    private static int N;

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            isCompleted = true;

            for (int cnt = 0; cnt < N; cnt++) {
                st = new StringTokenizer(br.readLine());
                st.nextToken();

                int nodeInfoCnt = st.countTokens();
                String nodeVal = st.nextToken();

                if (isLeafNode(nodeInfoCnt) && isOperator(nodeVal) || !isLeafNode(nodeInfoCnt) && !isOperator(nodeVal)) {
                    isCompleted = false;
                    for (int c = cnt+1; c < N; c++) br.readLine();
                    break;
                }
            }

            sb.append("#"+t+" "+(isCompleted?1:0)+"\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static boolean isOperator(String nodeVal) {
        return set.contains(nodeVal);
    }

    private static boolean isLeafNode(int nodeInfoCnt) {
        return nodeInfoCnt == 1;
    }
}

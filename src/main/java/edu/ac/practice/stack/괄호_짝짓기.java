package edu.ac.practice.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * @author noah kim
 * @date 2024/02/04
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV14eWb6AAkCFAYD
 * @keyword_solution
    Requirements: 괄호문자들로 구성된 문자열에서 괄호의 짝이 모두 맞는지 판별하라.

    [괄호문자]
    - 종류: '()', '[]', '{}', '<>'
 * @input TC(10)
    - 괄호문자열 길이
    - 괄호문자열
 * @output
    - 유효성 여부(1-유효, 0-유효하지 않음)
 * @time_complex O(N)
 * @perf 19,416 kb / 104 ms
 */
public class 괄호_짝짓기 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final Stack<Character> stack = new Stack<>();
    private static final String opendBrackets = "({[<";
    private static final String closedBrackets = ")}]>";
    private static final int T = 10;
    private static int length;

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            Integer.parseInt(br.readLine());
            sb.append("#" + t + " " + isValid(br.readLine()) + "\n");
            stack.clear();
        }

        System.out.println(sb);

        br.close();
    }

    private static char isValid(String inputStr) {
        for (char c : inputStr.toCharArray()) {
            String curS = String.valueOf(c);
            if (opendBrackets.contains(curS)) {
                stack.push(c);
            } else if (stack.isEmpty()) {
                return '0';
            } else if (closedBrackets.indexOf(curS) == opendBrackets.indexOf(stack.peek())) {
                stack.pop();
            } else {
                return '0';
            }
        }

        return stack.size() == 0 ? '1' : '0';
    }
}

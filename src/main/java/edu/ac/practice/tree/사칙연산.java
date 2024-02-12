package edu.ac.practice.tree;

import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/12
 * @link https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV141J8KAIcCFAYD
 * @requirement 주어진 수식트리의 결과를 출력하라.
 * @summary
    [노드]
    - 값: 양의 정수 or 연산자(사칙연산)
 * @input TC: 10
    - N: 총 정점 개수(1<=N<=1_000)
    - 노드 번호, 값, 왼쪽 자식 번호, 오른쪽 자식 번호
 * @output
 * @time_complex 25,408 kb / 129 ms
 * @perf
 */
public class 사칙연산 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final String OP_PLUS = "+";
    private static final String OP_MINUS = "-";
    private static final String OP_MUL = "*";
    private static final String OP_DIV = "/";
    private static final int ROOT_NODE = 1;
    private static final int T = 10;
    private static StringTokenizer st;
    private static Node[] tree;
    private static Node start;
    private static Object val;
    private static int N, lcIdx, rcIdx;

    static class Node {
        String op;
        int val, left, right;

        public Node(Object arg) {
            if (arg instanceof String) this.op = (String) arg;
            else if (arg instanceof Number) this.val = ((Number) arg).intValue();
        }

        public boolean isLeafNode() {
            return Objects.isNull(this.op);
        }

        public boolean isPlusOp() {
            return !isLeafNode() && this.op.equals(OP_PLUS);
        }

        public boolean isMinusOp() {
            return !isLeafNode() && this.op.equals(OP_MINUS);
        }

        public boolean isMulOp() {
            return !isLeafNode() && this.op.equals(OP_MUL);
        }

        public void addLeft(int left) {
            this.left = left;
        }
        public void addRight(int right) {
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            tree = new Node[N*2+1];

            for (int num = 1; num <= N; num++) {
                st = new StringTokenizer(br.readLine());
                st.nextToken();

                String opOrNum = st.nextToken();
                switch(opOrNum.charAt(0)) {
                    case '+': val = OP_PLUS; break;
                    case '-': val = OP_MINUS; break;
                    case '*': val = OP_MUL; break;
                    case '/': val = OP_DIV; break;
                    default: val = Integer.parseInt(opOrNum);
                }

                Node cur = new Node(val);

                if (st.hasMoreTokens()) {
                    lcIdx = Integer.parseInt(st.nextToken());
                    cur.addLeft(lcIdx);

                    rcIdx = Integer.parseInt(st.nextToken());
                    cur.addRight(rcIdx);
                }

                tree[num] = cur;
            }

            sb.append("#" + t + " ")
              .append((int) dfs(ROOT_NODE))
              .append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static double dfs(int start) {
        Node cur = tree[start];
        if (cur.isLeafNode()) return cur.val;

        double lRst = dfs(cur.left);
        double rRst = dfs(cur.right);

        if (cur.isPlusOp()) return lRst + rRst;
        else if (cur.isMinusOp()) return lRst - rRst;
        else if (cur.isMulOp()) return lRst * rRst;

        return lRst / rRst;
    }
}

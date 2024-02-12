package edu.ac.practice.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/12
 * @link https://swexpertacademy.com/main/code/codeBattle/problemDetail.do?contestProbId=AV140YnqAIECFAYD&categoryId=AY1INdsqPvADFAWX&categoryType=BATTLE&battleMainPageIndex=1
 * @requirement 주어진 완전이진트리를 중위순회로 탐색하여 노드의 겂을 순서대로 출력하라.
 * @summary
    [노드]
    - 값: 문자형. 1개
 * @input TC: 10
    - N: 정점 수 (1<=N<=100)
    - 정점 정보: 순번, 정점 값, 왼쪽 자식 번호, 오른쪽 자식 번호
 * @output
 * @time_complex O(N)
 * @perf 20308kb / 113ms
 */
public class 중위_순회 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer(), ans = new StringBuffer();
    private static char[] tree;
    private static final int T = 10;
    private static final int ROOT_NODE_NUM = 1;
    private static StringTokenizer st;
    private static int N, num, lcIdx, rcIdx;
    private static char val;

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            tree = new char[N*2+2];
            ans.setLength(0);

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                num = Integer.parseInt(st.nextToken());
                val = st.nextToken().charAt(0);

                if (isMiddleNode()) {
                    if (hasOnlyLeftChild()) {
                        lcIdx = Integer.parseInt(st.nextToken());
                    } else if (hasTwoChildren()) {
                        lcIdx = Integer.parseInt(st.nextToken());
                        rcIdx = Integer.parseInt(st.nextToken());
                    }
                }

                tree[num] = val;
            }

            dfs(ROOT_NODE_NUM);

            sb.append("#" + t + " ")
              .append(ans)
              .append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static boolean isMiddleNode() {
        return st.countTokens() > 0;
    }

    private static boolean hasOnlyLeftChild() {
        return st.countTokens() == 1;
    }

    private static boolean hasTwoChildren() {
        return st.countTokens() == 2;
    }

    private static void dfs(int start) {
        if (tree[start] == 0) return;

        dfs(start*2);
        ans.append(tree[start]);
        dfs(start*2+1);
    }
}

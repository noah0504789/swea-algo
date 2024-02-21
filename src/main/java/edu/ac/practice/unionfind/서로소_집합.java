package edu.ac.practice.unionfind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024-02-21
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AWBJKA6qr2oDFAWr&probBoxId=AY3JcG16dgMDFAXh&type=PROBLEM&problemBoxTitle=0219%EC%A3%BC&problemBoxCnt=1
 * @requirement 주어진 집합들을 대상으로 명령을 수행하고, 두 집합이 같은 집합에 속해있는지 명령순으로 출력하라.
 * @summary
    [집합]
    - 1부터 N까지
    - 집합 수: N개
    - 원소 수: 각각 한개의 원소

    [명령]
    - 0: 두 원소의 집합을 하나로 합치기
    - 1: 두 원소가 같은 집합에 속하는지 여부
 * @keypoint
 * @input TC: input
    - N: 원소의 마지막 수(1<=N<=1_000_000), M: 연산 수(1<=M<=100_000)
 * @output
 * @time_complex
 * @perf 107,588 kb / 466 ms
 */
public class 서로소_집합 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final int UNION_CMD = 0;
    private static final int CHECK_SAME_GROUP_CMD = 1;
    private static StringTokenizer st;
    private static UnionFind unionFind;
    private static int TC, N, M, cmd, x, y;
    static final class UnionFind {
        final int size;
        int[] root, rank;

        public UnionFind(int size) {
            this.size = size;
            root = new int[size+1];
            rank = new int[size+1];

            for (int i = 1; i <= size; i++) {
                root[i] = i;
                rank[i] = 1;
            }
        }

        public boolean union(int x, int y) {
            if (isCycle(x, y)) return false;

            int rootX = root[x], rootY = root[y];
            int rankX = rank[x], rankY = rank[y];

            if (rankX > rankY) root[rootY] = root[rootX];
            else root[rootX] = root[rootY];

            if (rankX == rankY) rank[rootY]++;

            return true;
        }

        public boolean isCycle(int x, int y) {
            return find(x) == find(y);
        }

        private int find(int x) {
            if (x == root[x]) return x;

            return root[x] = find(root[x]);
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            sb.append("#"+t+" ");

            unionFind = new UnionFind(N);

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                cmd = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());

                if (cmd == UNION_CMD) {
                    unionFind.union(x, y);
                } else if (cmd == CHECK_SAME_GROUP_CMD) {
                    if (unionFind.isCycle(x, y)) sb.append(1);
                    else sb.append(0);
                }
            }

            sb.append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}

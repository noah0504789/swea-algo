package edu.ac.practice.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/22
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV_mSnmKUckDFAWb&probBoxId=AY3JcG16dgMDFAXh&type=PROBLEM&problemBoxTitle=0219%EC%A3%BC&problemBoxCnt=3
 * @requirement 주어진 그래프의 최소 스패닝 트리의 가중치 합을 구하라.
 * @summary
    [최소 스패닝 트리]
    - 모든 정점을 연결하는 부분 그래프 중, 가중치의 합이 최소인 트리
 * @input TC: input
    - V: 정점의 개수(1<=V<=100_000), E: 간선의 개수(1<=E<=200_000)
    - 간선정보: A(src), B(dest), C(weight. 정수)
 * @output
 * @time_complex
 * @perf
 */
public class 최소_스패닝_트리 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final PriorityQueue<Edge> pq = new PriorityQueue<>();
    private static StringTokenizer st;
    private static UnionFind unionFind;
    private static int TC, V, E, src, dest, weight, cnt;
    private static long ans;
    static class UnionFind {
        final int size;
        int[] root;

        public UnionFind(int size) {
            this.size = size;
            this.root = new int[size+1];
            makeSet();
        }

        private void makeSet() {
            for (int num = 1; num <= size; num++) {
                root[num] = num;
            }
        }

        private int find(int x) {
            if (x == root[x]) return x;

            return root[x] = find(root[x]);
        }

        public boolean union(int x, int y) {
            int rootX = find(x), rootY = find(y);

            if (rootX == rootY) return false;

            root[rootX] = root[rootY];

            return true;
        }
    }
    static class Edge implements Comparable<Edge> {
        final int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(br.readLine());

        for (int t = 1; t <= TC; t++) {
            ans = 0;
            cnt = 0;
            pq.clear();

            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            unionFind = new UnionFind(V);

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                src = Integer.parseInt(st.nextToken());
                dest = Integer.parseInt(st.nextToken());
                weight = Integer.parseInt(st.nextToken());

                pq.offer(new Edge(src, dest, weight));
            }

            while (!pq.isEmpty()) {
                Edge curE = pq.poll();
                int src = curE.src, dest = curE.dest, weight = curE.weight;
                if (unionFind.union(src, dest)) {
                    cnt++;
                    ans += weight;
                }
                if (cnt == V-1) break;
            }

            sb.append("#"+t+" ").append(ans).append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}

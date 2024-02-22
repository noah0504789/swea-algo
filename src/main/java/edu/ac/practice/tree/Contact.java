package edu.ac.practice.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author noah kim
 * @date 2024-02-22
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AV15B1cKAKwCFAYD&probBoxId=AY3JcG16dgMDFAXh&type=PROBLEM&problemBoxTitle=0219%EC%A3%BC&problemBoxCnt=3
 * @requirement 가장 나중에 연락을 받게 되는 사람 중, 번호가 가장 큰 사람을 구하라
 * @summary
    [비상 연락망]
    - 사람 별 연락할 수 있는 관계
    - undirect
 * @input TC: input
    - 데이터 길이, 시작점
    - [from, to] * 데이터 길이
 * @output
 * @time_complex
 * @perf 19,448 kb / 128 ms
 */
public class Contact {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final Queue<int[]> queue = new LinkedList<>();
    private static final int TC = 10;
    private static StringTokenizer st;
    private static int start, edgeCnt, src, dest, curOrder, curMaxNum;
    private static boolean[] visited;
    private static Node[] nodes;
    static class Node {
        final int num;
        Set<Node> nexts;

        public Node(int num) {
            this.num = num;
            nexts = new HashSet<>();
        }

        public void addNext(Node next) {
            nexts.add(next);
        }

        public boolean hasNext(Node next) {
            return nexts.contains(next);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return num == node.num;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= TC; t++) {
            nodes = new Node[101];
            visited = new boolean[101];

            st = new StringTokenizer(br.readLine());
            edgeCnt = Integer.parseInt(st.nextToken());
            start = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            for (int e = 0; e < edgeCnt/2; e++) {
                src = Integer.parseInt(st.nextToken());
                dest = Integer.parseInt(st.nextToken());

                Node cur = nodes[src] == null ? nodes[src] = new Node(src) : nodes[src];
                Node next = nodes[dest] == null ? nodes[dest] = new Node(dest) : nodes[dest];

                if (cur.hasNext(next)) continue;
                cur.addNext(next);
            }

            bfs();

            sb.append("#"+t+" ").append(curMaxNum).append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void bfs() {
        curOrder = 0;
        curMaxNum = start;
        queue.offer(new int[]{curMaxNum, curOrder});
        visited[curMaxNum] = true;

        while (!queue.isEmpty()) {
            int[] me = queue.poll();
            int meNum = me[0], meOrder = me[1];
            if (meOrder > curOrder) {
                curMaxNum = meNum;
                curOrder = meOrder;
            } else {
                curMaxNum = Math.max(curMaxNum, meNum);
            }

            for (Node colleague : nodes[meNum].nexts) {
                int colNum = colleague.num;
                if (visited[colNum]) continue;
                visited[colNum] = true;

                queue.offer(new int[]{colNum, meOrder+1});
            }
        }
    }
}

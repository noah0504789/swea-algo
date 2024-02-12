package edu.ac.practice.linkedlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/12
 * @link https://swexpertacademy.com/main/code/codeBattle/problemDetail.do?contestProbId=AX5MBiQqAbQDFASv&categoryId=AY1INdsqPvADFAWX&categoryType=BATTLE&battleMainPageIndex=1
 * @requirement 편집된 수열의 L번째 항을 출력하라. (L번째 항이 존재하지 않으면, -1 출력)
 * @summary
    [수열]
    - 길이: N
    - 값 범위: 1-1_000_000_000

    [편집]
    - 횟수: M
    - 명령
        1. 삽입: I x y (x번 인덱스 앞에 y항 추가)
        2. 삭제: D x (x번 인덱스 항 삭제)
        3. 교체: C x y (x번 인덱스 항의 값을 y로 교체)
 * @input (TC: input)
    - N: 수열의 길이(5<=N<=1_000), M: 편집 횟수(1<=M<=1_000), L: 출력할 인덱스 번호(6<=L<=N+M)
    - 수열
    - 전체 편집 명령
 * @output
 * @time_complex O(N)
 * @perf 24208kb / 129ms
 */
public class 수열_편집 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static final MyLinkedList linkedList = new MyLinkedList();
    private static final int NOT_EXIST = -1;
    private static StringTokenizer st;
    private static char INS_COMMAND = 'I';
    private static char DEL_COMMAND = 'D';
    private static char CHG_COMMAND = 'C';
    private static int T, N, M, L, ans;

    static final class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }

        public void connect(Node next) {
            this.next = next;
        }

        public void disconnect() {
            this.next = null;
        }
    }

    static final class MyLinkedList {
        Node head, tail;
        int size;

        public MyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }


        public void addFirst(int val) {
            Node newNode = new Node(val);

            head = newNode;
            tail = newNode;

            size++;
        }

        public void addLast(int val) {
            if (head == null) {
                addFirst(val);
                return;
            }

            Node newNode = new Node(val);
            tail.connect(newNode);
            tail = tail.next;

            size++;
        }

        public void add(int idx, int val) {
            Node prev = head;
            prev = getNode(idx - 1, prev);

            if (prev == tail) {
                addLast(val);
                return;
            }

            Node newNode = new Node(val);

            newNode.connect(prev.next);
            prev.connect(newNode);

            size++;
        }

        public void del(int idx) {
            Node prev = head;
            prev = getNode(idx - 1, prev);
            Node target = prev.next;

            if (target == tail) {
                delLast(prev);
                return;
            }

            Node next = target.next;
            prev.connect(next);

            size--;
        }

        public void delLast(Node prev) {
            tail = prev;
            prev.disconnect();

            size--;
        }

        public void change(int idx, int val) {
            Node target = head;

            target = getNode(idx, target);

            target.val = val;
        }

        public int getVal(int idx) {
            Node target = head;

            return getNode(idx, target).val;
        }

        public void clear() {
            head = null;
            tail = null;

            size = 0;
        }

        private Node getNode(int idx, Node node) {
            for (int i = 0; i < idx; i++) node = node.next;

            return node;
        }
    }

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            linkedList.clear();
            ans = NOT_EXIST;

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            while (st.hasMoreTokens()) {
                linkedList.addLast(Integer.parseInt(st.nextToken()));
            }

            for (int cmdCnt = 0; cmdCnt < M; cmdCnt++) {
                st = new StringTokenizer(br.readLine());

                char operator = st.nextToken().charAt(0);
                if (operator == INS_COMMAND) {
                    int idxToAdd = Integer.parseInt(st.nextToken());
                    int numToAdd = Integer.parseInt(st.nextToken());
                    linkedList.add(idxToAdd, numToAdd);
                } else if (operator == DEL_COMMAND) {
                    int idxToDel = Integer.parseInt(st.nextToken());
                    linkedList.del(idxToDel);
                } else if (operator == CHG_COMMAND) {
                    int idxToChg = Integer.parseInt(st.nextToken());
                    int numToChg = Integer.parseInt(st.nextToken());
                    linkedList.change(idxToChg, numToChg);
                }
            }

            sb.append("#" + t + " ");

            if (L < linkedList.size) ans = linkedList.getVal(L);

            sb.append(ans)
              .append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}
